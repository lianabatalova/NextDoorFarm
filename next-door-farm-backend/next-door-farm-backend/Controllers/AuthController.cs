using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Text.Json.Serialization;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using next_door_farm_backend.Models;
using next_door_farm_backend.Data;

namespace next_door_farm_backend.Controllers
{

    public class UserLogInDto
    {
        public string username { get; set; }
        public string password { get; set; }
        public string userType { get; set; }
    }

    public class UserLoggedInDto // same for UserSignedUpDto
    {
        public Guid id { get; set; }
        public string username { get; set; }
        public string userType { get; set; }
        public string token { get; set; }
        public int ttl { get; set; }
    }

    public class UserSignUpDto
    {
        public string firstName { get; set; }
        public string lastName { get; set; }
        public string username { get; set; }
        public string email { get; set; }
        public string password { get; set; }
        public string userType { get; set; }
    }
    
    public class AuthController : Controller
    {
        private const int TokenTimeoutInMinutes = 100;
        
        private ApplicationDbContext db;
        private IConfiguration _config;

        public AuthController(IConfiguration config, ApplicationDbContext context)
        {
            _config = config;
            db = context;
        }
        
        // GET
        public IActionResult Index()
        {
            return Content("Auth Controller");
        }

        [AllowAnonymous]
        [HttpPost]
        [Route("log-in")]
        public IActionResult Login([FromBody]UserLogInDto userLogInDto)
        {
            IActionResult response = Unauthorized();    
            var user = AuthenticateUser(userLogInDto);    
    
            if (user != null)    
            {    
                var tokenString = GenerateJsonWebToken(user);
                user.token = tokenString;
                user.ttl = TokenTimeoutInMinutes;
                response = Ok(user);    
            }    
    
            return response;  
        }
        
        private string GenerateJsonWebToken(UserLoggedInDto userInfo)    
        {    
            var securityKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["Jwt:Key"]));    
            var credentials = new SigningCredentials(securityKey, SecurityAlgorithms.HmacSha256);

            var claims = new[]
            {
                new Claim("id", userInfo.id.ToString()),
                new Claim("username", userInfo.username),
                new Claim("userType", userInfo.userType)
            };
            
            var token = new JwtSecurityToken(_config["Jwt:Issuer"],    
                _config["Jwt:Issuer"],    
                claims,    
                expires: DateTime.Now.AddMinutes(TokenTimeoutInMinutes),    
                signingCredentials: credentials);    
    
            return new JwtSecurityTokenHandler().WriteToken(token);    
        } 
        
        private UserLoggedInDto AuthenticateUser(UserLogInDto userLogInDto)    
        {    
            UserLoggedInDto user = null;
            Customers customer = null;
            Farmers farmer = null;
            
            if (userLogInDto is null) return null;
            
            if (userLogInDto.userType == "customer")
            {
                customer = db.Customers
                    .FirstOrDefault(customer_ => customer_.Username == userLogInDto.username &&
                                                customer_.Password == userLogInDto.password);
            }else if (userLogInDto.userType == "farmer")
            {
                 farmer = db.Farmers
                    .FirstOrDefault(farmer_ => farmer_.Username == userLogInDto.username &&
                                                                       farmer_.Password == userLogInDto.password);
            }
            
            if ((customer is not null) || (farmer is not null))
            {    
                user = new UserLoggedInDto { id = ((userLogInDto.userType == "customer") ? customer.RefID : farmer.RefID), 
                    username = userLogInDto.username, 
                    userType = userLogInDto.userType};    
            }
            return user;    
        }

        /*[Route("test_login")]
        [HttpGet]
        [Authorize]
        public IActionResult test_login()
        {
            var currentUser = HttpContext.User;

            StringBuilder builtString = new StringBuilder();
            foreach (var claim in currentUser.Claims)
            {
                builtString.Append("Type: " + claim.Type + "\n" + "Value: " + claim.Value + "\n\n");
            }

            return Ok(builtString.ToString());
        }*/
        
        
        [AllowAnonymous]
        [HttpPost]
        [Route("sign-up")]
        public IActionResult Signup([FromBody]UserSignUpDto userSignUpDto)
        {
            UserLogInDto user = null;
            if (userSignUpDto.username is null || userSignUpDto.password is null) return null;
            
            // yes, it looks ridiculous without abstract class
            if (userSignUpDto.userType == "customer")
            {
                var customerInDb = db.Customers.FirstOrDefault(customer => customer.Username == userSignUpDto.username);
                if (customerInDb is null)
                {
                    Customers customer = new Customers()
                    {
                        Email = userSignUpDto.email ?? "",
                        FirstName = userSignUpDto.firstName ?? "",
                        SecondName = userSignUpDto.lastName ?? "",
                        Password = userSignUpDto.password,
                        RefID = new Guid(),
                        Username = userSignUpDto.username
                    };
                    db.Customers.Add(customer);
                    db.SaveChanges();
                    
                    user = new UserLogInDto()
                    {
                        username = userSignUpDto.username, 
                        password = userSignUpDto.password,
                        userType = userSignUpDto.userType
                    };
                }
            }else if (userSignUpDto.userType == "farmer")
            {
                var farmerInDb = db.Farmers.FirstOrDefault(farmer => farmer.Username == userSignUpDto.username);
                if (farmerInDb is null)
                {
                    Farmers farmer = new Farmers()
                    {
                        Email = userSignUpDto.email ?? "",
                        FirstName = userSignUpDto.firstName ?? "",
                        SecondName = userSignUpDto.lastName ?? "",
                        Password = userSignUpDto.password,
                        RefID = new Guid(),
                        Username = userSignUpDto.username
                    };
                    db.Farmers.Add(farmer);
                    db.SaveChanges();
                    
                    user = new UserLogInDto()
                    {
                        username = userSignUpDto.username, 
                        password = userSignUpDto.password,
                        userType = userSignUpDto.userType
                    };
                }
            }

            return Login(user);
        }
    }    
    
}
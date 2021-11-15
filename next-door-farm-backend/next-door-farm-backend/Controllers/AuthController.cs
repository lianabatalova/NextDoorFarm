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
        public int id { get; set; }
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
        public string userType { get; set; }
        public string password { get; set; }
    }
    
    public class AuthController : Controller
    {
        private const int TokenTimeoutInMinutes = 100;
        
        private IConfiguration _config;

        public AuthController(IConfiguration config)
        {
            _config = config;
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
                new Claim(JwtRegisteredClaimNames.Sub, (userInfo.id).ToString()),
                new Claim(JwtRegisteredClaimNames.GivenName, userInfo.username),
                new Claim(JwtRegisteredClaimNames.Typ, userInfo.userType)
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
            
            if (userLogInDto.username == "demo")    // replace with database checking and auth
            {    
                user = new UserLoggedInDto { id = 0, username = userLogInDto.username, 
                    userType = userLogInDto.userType};    
            }    
            return user;    
        }

        [Route("test_login")]
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
        }
        
        
        [AllowAnonymous]
        [HttpPost]
        [Route("sign-up")]
        public IActionResult Signup([FromBody]UserSignUpDto userSignUpDto)
        {
            // Try registering user (DB queries)
            var user = new UserLogInDto()
            {
                username = userSignUpDto.username, password = userSignUpDto.password,
                userType = userSignUpDto.userType
            };
            return Login(user);
        }
    }    
    
}
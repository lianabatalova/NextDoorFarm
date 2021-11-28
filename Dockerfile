FROM mcr.microsoft.com/dotnet/sdk:5.0 AS base
WORKDIR /app
EXPOSE 5000
ENV ASPNETCORE_URLS=http://*:5000

FROM mcr.microsoft.com/dotnet/sdk:5.0 AS build
WORKDIR /src
COPY ["next-door-farm-backend/next-door-farm-backend/next-door-farm-backend.csproj", "./"]
RUN dotnet restore "./next-door-farm-backend.csproj"
COPY next-door-farm-backend/next-door-farm-backend/. .
WORKDIR /src/.
RUN dotnet build "next-door-farm-backend.csproj" -c Debug -o /app/build

FROM build AS publish
RUN dotnet publish "next-door-farm-backend.csproj" -c Debug -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT [ "dotnet", "next-door-farm-backend.dll" ]

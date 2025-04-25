# Architecture Microservices

Ce dossier contient l'ensemble des microservices backend de notre application.

## Structure des Microservices

- **microservice-express/** - Microservice Node.js/Express pour la gestion des produits
- **school/** - Microservice Spring Boot pour la gestion des écoles
- **discovery/** - Service Eureka pour la découverte de services
- **gateway/** - API Gateway Spring Cloud Gateway
- **config-server/** - Serveur de configuration Spring Cloud Config
- **student/** - Microservice de gestion des étudiants
- **university/** - Microservice de gestion des universités
- **class/** - Microservice de gestion des classes

## Prérequis

- JDK 11+ pour les microservices Spring Boot
- Node.js (v14+) pour le microservice Express
- MongoDB
- Docker et Docker Compose (pour le déploiement)

## Démarrage avec Docker Compose

Pour lancer l'ensemble des microservices:

```bash
docker-compose up -d
```

## Démarrage Manuel des Services

### Microservice Express

```bash
cd microservice-express
npm install
npm run dev
```

### Microservices Spring Boot

Pour chaque microservice Spring Boot (school, discovery, gateway, etc.):

```bash
cd <nom-du-microservice>
mvn clean install
mvn spring-boot:run
```

## Ports des Services

- **Discovery Service (Eureka)** - 8761
- **Config Server** - 8888
- **Gateway** - 8080
- **School Service** - 8081
- **Student Service** - 8082
- **Class Service** - 8083
- **University Service** - 8084
- **Microservice Express** - 3000

## Communication entre Services

Les microservices communiquent entre eux via:
- API REST
- Service Discovery avec Eureka
- Load Balancing avec Spring Cloud LoadBalancer
- Circuit Breaker avec Resilience4j

## Architecture

                           ┌─────────────┐
                           │             │
                           │   Gateway   │
                           │             │
                           └─────┬───────┘
                                 │
                 ┌───────────────┼───────────────┐
                 │               │               │
          ┌──────▼─────┐  ┌──────▼─────┐         │
          │  Express   │  │   School   │◄┐       │
          │   (Mongo)  │  │   (MySQL)  │ ├───────┘
          └──────┬─────┘  └──────┬─────┘ │
                 │               │       │
          ┌──────▼──────┐  ┌─────▼──────┐│  ┌─────────────┐
          │  MongoDB    │  │  MySQL     │└─►│ University  │
          └─────────────┘  └──────┬─────┘   │  (MySQL)    │
                                  │         └─────────────┘
                            ┌─────▼─────┐  
                            │  Student  │  
                            │  (MySQL)  │ 
                            └───────────┘ 
``` 

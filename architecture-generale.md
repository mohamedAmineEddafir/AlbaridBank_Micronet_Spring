# Architecture Générale du Système AlbaridBank

## Vue d'ensemble

L'application AlbaridBank est une solution bancaire moderne basée sur une architecture microservices, utilisant Spring Boot pour le backend et Angular pour le frontend. Cette architecture distribuée assure la scalabilité, la résilience et la maintenabilité du système.

## Diagramme d'Architecture Générale

```
┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                                    COUCHE PRÉSENTATION                                       │
├─────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                             │
│    ┌─────────────────────────────────────────────────────────────────────────────────┐    │
│    │                           Angular Frontend                                      │    │
│    │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐           │    │
│    │  │  Dashboard  │  │   Comptes   │  │   Clients   │  │   Agences   │           │    │
│    │  │     UI      │  │     UI      │  │     UI      │  │     UI      │           │    │
│    │  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘           │    │
│    │                                                                                 │    │
│    │  Technologies: TypeScript, Angular Material, ECharts, SCSS                    │    │
│    │  Outils: VS Code, Angular CLI                                                 │    │
│    └─────────────────────────────────────────────────────────────────────────────────┘    │
│                                         │                                                   │
│                                         │ HTTP/HTTPS                                        │
│                                         ▼                                                   │
└─────────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                                  COUCHE API GATEWAY                                         │
├─────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                             │
│    ┌─────────────────────────────────────────────────────────────────────────────────┐    │
│    │                      Spring Cloud Gateway                                      │    │
│    │                                                                                 │    │
│    │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐           │    │
│    │  │   Routing   │  │    CORS     │  │   Rate      │  │   Load      │           │    │
│    │  │   Service   │  │   Config    │  │  Limiting   │  │ Balancing   │           │    │
│    │  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘           │    │
│    │                                                                                 │    │
│    │  Port: 8080 | JWT Token Validation | Request Routing                         │    │
│    └─────────────────────────────────────────────────────────────────────────────────┘    │
│                                         │                                                   │
│                                         │ Inter-Service Communication                       │
│                                         ▼                                                   │
└─────────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                                 COUCHE SÉCURITÉ                                            │
├─────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                             │
│    ┌─────────────────────────────────────────────────────────────────────────────────┐    │
│    │                        Keycloak Authentication                                  │    │
│    │                                                                                 │    │
│    │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐           │    │
│    │  │     SSO     │  │   OAuth2    │  │    JWT      │  │   RBAC      │           │    │
│    │  │   Service   │  │   Server    │  │   Tokens    │  │   Policies  │           │    │
│    │  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘           │    │
│    │                                                                                 │    │
│    │  Port: 9098 | Identity Provider | User Management                             │    │
│    └─────────────────────────────────────────────────────────────────────────────────┘    │
│                                         │                                                   │
│                                         │ Authentication & Authorization                     │
│                                         ▼                                                   │
└─────────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                                COUCHE MICROSERVICES                                        │
├─────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                             │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐      │
│  │  Discovery      │  │  Config         │  │   Customer      │  │   Branch        │      │
│  │  Service        │  │  Service        │  │   Service       │  │   Service       │      │
│  │  (Eureka)       │  │                 │  │                 │  │                 │      │
│  │  Port: 8761     │  │  Port: 8888     │  │  Port: 8081     │  │  Port: 8082     │      │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  └─────────────────┘      │
│                                                                                             │
│                       ┌─────────────────┐  ┌─────────────────┐                           │
│                       │   Edition       │  │   Transaction   │                           │
│                       │   Service       │  │   Service       │                           │
│                       │                 │  │   (Planifié)    │                           │
│                       │  Port: 8083     │  │  Port: 8084     │                           │
│                       └─────────────────┘  └─────────────────┘                           │
│                                                                                             │
│  Technologies: Spring Boot, Spring Cloud, Feign Client, OpenAPI/Swagger                   │
│  Communication: REST APIs, Service Discovery, Load Balancing                               │
└─────────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                                   COUCHE DONNÉES                                           │
├─────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                             │
│  ┌─────────────────────────────────┐      ┌─────────────────────────────────┐             │
│  │         PostgreSQL              │      │          Informix               │             │
│  │     (Développement)             │      │       (Production)              │             │
│  │                                 │      │                                 │             │
│  │  ┌─────────────┐                │      │  ┌─────────────┐                │             │
│  │  │   Clients   │                │      │  │   Clients   │                │             │
│  │  │   Comptes   │                │      │  │   Comptes   │                │             │
│  │  │   Agences   │                │      │  │   Agences   │                │             │
│  │  │   Transactions │              │      │  │   Transactions │              │             │
│  │  └─────────────┘                │      │  └─────────────┘                │             │
│  │                                 │      │                                 │             │
│  │  Port: 5436                     │      │  Port: Production               │             │
│  └─────────────────────────────────┘      └─────────────────────────────────┘             │
│                                                                                             │
│  Administration: PgAdmin (Port: 5050)                                                      │
│  ORM: Spring Data JPA, Hibernate                                                           │
└─────────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                               COUCHE MONITORING                                            │
├─────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                             │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐      │
│  │     Zipkin      │  │    Grafana      │  │      Loki       │  │   Prometheus    │      │
│  │ (Distributed    │  │  (Dashboards)   │  │     (Logs)      │  │   (Metrics)     │      │
│  │   Tracing)      │  │                 │  │                 │  │                 │      │
│  │  Port: 9411     │  │  Port: 3000     │  │  Port: 3100     │  │  Port: 9090     │      │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  └─────────────────┘      │
│                                                                                             │
│                                  ┌─────────────────┐                                      │
│                                  │    Promtail     │                                      │
│                                  │  (Log Collector)│                                      │
│                                  │                 │                                      │
│                                  │  Port: 9080     │                                      │
│                                  └─────────────────┘                                      │
│                                                                                             │
│  Fonctionnalités: Tracing distribué, Métriques, Logs centralisés, Alerting               │
└─────────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                              COUCHE INFRASTRUCTURE                                         │
├─────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐      │
│  │                            Docker Ecosystem                                     │      │
│  │                                                                                 │      │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐           │      │
│  │  │   Docker    │  │   Docker    │  │   Docker    │  │   Docker    │           │      │
│  │  │  Compose    │  │  Containers │  │   Images    │  │   Networks  │           │      │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘           │      │
│  │                                                                                 │      │
│  │  Network: microservices-net (Bridge)                                          │      │
│  │  Volumes: postgres, pgadmin                                                   │      │
│  └─────────────────────────────────────────────────────────────────────────────────┘      │
│                                                                                             │
│  Outils de Développement: IntelliJ IDEA, VS Code, Postman, Git/GitHub                     │
│  Orchestration: Docker Compose, Spring Cloud                                               │
└─────────────────────────────────────────────────────────────────────────────────────────────┘
```

## Description Détaillée des Couches

### 1. Couche Présentation (Frontend)

**Technologies Utilisées :**
- **Angular** : Framework principal avec TypeScript
- **Angular Material** : Composants UI modernes et responsive
- **ECharts** : Bibliothèque de visualisation pour graphiques et dashboards
- **SCSS** : Préprocesseur CSS pour un styling avancé
- **VS Code & Angular CLI** : Environnement de développement optimisé

**Fonctionnalités :**
- Interface utilisateur moderne et intuitive
- Dashboards interactifs avec visualisations de données
- Gestion des comptes clients et CCP
- Interface d'administration des agences
- Authentification SSO intégrée

### 2. Couche API Gateway

**Spring Cloud Gateway :**
- **Point d'entrée unique** : Toutes les requêtes frontend passent par le gateway
- **Routage intelligent** : Redirection vers les microservices appropriés
- **Load Balancing** : Distribution des charges entre instances
- **Configuration CORS** : Gestion des requêtes cross-origin
- **Rate Limiting** : Protection contre les attaques DDoS
- **JWT Token Validation** : Validation des tokens d'authentification

**Port d'écoute :** 8080

### 3. Couche Sécurité

**Keycloak Authentication Server :**
- **SSO (Single Sign-On)** : Authentification unique pour tous les services
- **OAuth2/OpenID Connect** : Protocoles standards d'authentification
- **JWT Tokens** : Tokens sécurisés pour l'autorisation
- **RBAC (Role-Based Access Control)** : Gestion fine des permissions
- **Identity Provider** : Gestion centralisée des identités
- **User Management** : Administration des utilisateurs et rôles

**Port d'écoute :** 9098

### 4. Couche Microservices

#### 4.1 Discovery Service (Eureka)
- **Service Registry** : Enregistrement et découverte des services
- **Health Checks** : Monitoring de l'état des services
- **Load Balancing** : Répartition automatique des charges
- **Port :** 8761

#### 4.2 Config Service
- **Configuration centralisée** : Gestion des configurations de tous les services
- **Environment Profiles** : Configurations par environnement (dev, prod)
- **Dynamic Refresh** : Mise à jour des configurations sans redémarrage
- **Port :** 8888

#### 4.3 Customer Service
- **Gestion des clients** : CRUD complet des données clients
- **Gestion des comptes** : Comptes courants, CCP, épargne
- **Pagination et recherche** : Interface optimisée pour gros volumes
- **Cache Redis** : Optimisation des performances
- **Port :** 8081

#### 4.4 Branch Service (Agence)
- **Gestion des agences** : Informations complètes des agences postales
- **Statistiques** : Métriques et analyses des performances
- **Géolocalisation** : Recherche par région et zone
- **Cache système** : Optimisation des requêtes fréquentes
- **Port :** 8082

#### 4.5 Edition Service (Rapports)
- **Génération de rapports** : Création automatique de rapports Excel
- **Analytics** : Analyses statistiques et métriques
- **Templates** : Modèles de rapports personnalisables
- **Scheduling** : Génération programmée de rapports
- **Port :** 8083

#### 4.6 Transaction Service (Planifié)
- **Traitement des transactions** : Gestion des opérations financières
- **Historique complet** : Suivi des mouvements
- **Validation métier** : Règles de gestion bancaires
- **Audit trail** : Traçabilité complète
- **Port :** 8084

**Technologies communes :**
- **Spring Boot** : Framework principal
- **Spring Cloud** : Écosystème microservices
- **Feign Client** : Communication inter-services
- **OpenAPI/Swagger** : Documentation API automatique
- **Spring Security** : Sécurisation des endpoints

### 5. Couche Données

#### 5.1 PostgreSQL (Développement)
- **Base de données relationnelle** : Stockage principal pour le développement
- **Schémas multiples** : Séparation logique des données
- **Transactions ACID** : Garantie de cohérence des données
- **Port :** 5436
- **Administration :** PgAdmin sur port 5050

#### 5.2 Informix (Production)
- **Base de données legacy** : Système existant en production
- **Haute performance** : Optimisée pour les charges bancaires
- **Compatibilité** : Support des applications existantes
- **Migration progressive** : Transition vers PostgreSQL

**Technologies ORM :**
- **Spring Data JPA** : Abstraction d'accès aux données
- **Hibernate** : ORM principal
- **Connection Pooling** : Gestion optimisée des connexions
- **Database Migration** : Flyway pour la gestion des schémas

### 6. Couche Monitoring et Observabilité

#### 6.1 Zipkin (Distributed Tracing)
- **Tracing distribué** : Suivi des requêtes à travers les services
- **Performance monitoring** : Identification des goulots d'étranglement
- **Dependency mapping** : Visualisation des dépendances
- **Port :** 9411

#### 6.2 Grafana (Dashboards)
- **Visualisation** : Dashboards interactifs et personnalisables
- **Alerting** : Notifications en temps réel
- **Multi-datasource** : Intégration Prometheus, Loki, Zipkin
- **Port :** 3000

#### 6.3 Loki (Log Aggregation)
- **Centralisation des logs** : Collecte de tous les logs système
- **Recherche avancée** : Requêtes rapides et flexibles
- **Retention policies** : Gestion automatique de l'archivage
- **Port :** 3100

#### 6.4 Prometheus (Metrics)
- **Collecte de métriques** : Monitoring des performances système
- **Time series database** : Stockage optimisé des métriques
- **Alerting rules** : Règles d'alertes personnalisées
- **Port :** 9090

#### 6.5 Promtail (Log Collector)
- **Agent de collecte** : Récupération des logs depuis les services
- **Parsing intelligent** : Extraction automatique des métadonnées
- **Shipping** : Envoi sécurisé vers Loki
- **Port :** 9080

### 7. Couche Infrastructure

#### 7.1 Docker Ecosystem
- **Conteneurisation** : Isolation et portabilité des services
- **Docker Compose** : Orchestration locale et de développement
- **Images optimisées** : Containers légers et sécurisés
- **Network isolation** : Réseau dédié `microservices-net`
- **Volume persistence** : Stockage persistant pour les données

#### 7.2 Outils de Développement
- **IntelliJ IDEA** : IDE principal pour le développement Java
- **VS Code** : IDE pour le frontend Angular
- **Postman** : Tests et validation des APIs
- **Git/GitHub** : Gestion de versions et collaboration
- **Maven** : Build et gestion des dépendances

## Flux de Données Principaux

### 1. Flux d'Authentification
```
Frontend → Gateway → Keycloak → JWT Token → Services
```

**Étapes détaillées :**
1. L'utilisateur saisit ses identifiants sur l'interface Angular
2. Le frontend envoie la requête au Gateway
3. Le Gateway redirige vers Keycloak pour authentification
4. Keycloak valide les identifiants et génère un JWT token
5. Le token est retourné au frontend via le Gateway
6. Toutes les requêtes suivantes incluent le JWT token
7. Chaque service valide le token avant traitement

### 2. Flux des Opérations Métier
```
Frontend → Gateway → Service Discovery → Microservice → Database
```

**Étapes détaillées :**
1. L'utilisateur effectue une action sur l'interface (ex: consulter un compte)
2. Le frontend envoie une requête HTTP au Gateway
3. Le Gateway consulte Eureka pour localiser le service approprié
4. Le service Customer traite la requête et consulte la base de données
5. La réponse remonte via le même chemin
6. L'interface est mise à jour avec les données reçues

### 3. Flux de Monitoring
```
Services → Zipkin/Loki → Grafana → Alertes
```

**Étapes détaillées :**
1. Chaque service génère des traces et logs
2. Zipkin collecte les traces de performance
3. Promtail collecte les logs et les envoie à Loki
4. Prometheus collecte les métriques système
5. Grafana agrège toutes les données pour visualisation
6. Les alertes sont déclenchées selon les règles définies

### 4. Flux de Configuration
```
Config Service → Services → Dynamic Refresh
```

**Étapes détaillées :**
1. Les configurations sont centralisées dans Config Service
2. Au démarrage, chaque service récupère sa configuration
3. Les changements de configuration peuvent être appliqués à chaud
4. Les services s'actualisent automatiquement sans redémarrage

## Avantages de l'Architecture

### 1. Scalabilité
- **Scalabilité horizontale** : Ajout d'instances selon la charge
- **Microservices indépendants** : Scaling sélectif par service
- **Load balancing automatique** : Répartition intelligente des requêtes
- **Resource optimization** : Utilisation efficace des ressources

### 2. Résilience
- **Fault isolation** : Panne d'un service n'affecte pas les autres
- **Circuit breaker** : Protection contre les cascades de pannes
- **Retry mechanisms** : Tentatives automatiques en cas d'échec
- **Graceful degradation** : Fonctionnement dégradé mais fonctionnel

### 3. Maintenabilité
- **Separation of concerns** : Chaque service a une responsabilité claire
- **Independent deployment** : Déploiement indépendant des services
- **Team autonomy** : Équipes peuvent travailler indépendamment
- **Technology diversity** : Choix technologique adapté par service

### 4. Sécurité
- **Centralized authentication** : Keycloak comme point de sécurité unique
- **JWT tokens** : Authentification stateless et sécurisée
- **API Gateway** : Point de contrôle centralisé
- **Role-based access** : Autorisation fine par rôle et service

### 5. Observabilité
- **Distributed tracing** : Visibilité complète des requêtes
- **Centralized logging** : Logs agrégés et recherchables
- **Real-time monitoring** : Surveillance en temps réel
- **Performance insights** : Analyse des performances détaillée

### 6. Flexibilité Technologique
- **Polyglot persistence** : Choix de base de données adapté
- **Framework flexibility** : Technologies adaptées par service
- **Cloud readiness** : Architecture prête pour le cloud
- **Container orchestration** : Déploiement simplifié

## Patterns Architecturaux Implementés

### 1. API Gateway Pattern
- Point d'entrée unique pour toutes les requêtes
- Routage, authentification, et load balancing centralisés
- Réduction de la complexité côté client

### 2. Service Discovery Pattern
- Enregistrement automatique des services
- Découverte dynamique des instances
- Health checks et load balancing intégrés

### 3. Configuration Externalization
- Configurations centralisées et versionnées
- Gestion par environnement
- Mise à jour dynamique sans redémarrage

### 4. Database per Service
- Isolation des données par service
- Choix technologique adapté aux besoins
- Évolutivité indépendante

### 5. Observability Pattern
- Logging, metrics, et tracing intégrés
- Monitoring proactif et alerting
- Debugging simplifié des systèmes distribués

## Conclusion

L'architecture AlbaridBank représente une solution moderne et robuste pour un système bancaire d'entreprise. Elle combine les meilleures pratiques des architectures microservices avec des technologies éprouvées pour garantir :

- **Performance** : Optimisation des ressources et temps de réponse
- **Sécurité** : Protection des données bancaires sensibles
- **Évolutivité** : Adaptation aux besoins métier croissants
- **Fiabilité** : Disponibilité et consistance des services
- **Maintenabilité** : Facilité d'évolution et de maintenance

Cette architecture constitue une base solide pour le développement et l'évolution future du système d'information d'AlbaridBank.
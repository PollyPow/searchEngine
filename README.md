# searchEngine

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Setup Instructions](#setup-instructions)
    - [Docker](...)
    - [Backend](#postgresql)
    - [Frontend](#opensearch)
3. [Filling in the OpenSearch index](#filling-in-the-opensearch-index)

## Prerequisites

Ensure you have the following installed on your system:

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- Java Development Kit (JDK) version `coretto 21` 
- Maven

## Setup Instructions

### Docker
Start Docker with Docker PlugIn or by running
```bash
docker-compose up -d
```
in the "searchEngine/backend" directory.

### Backend
Start Spring Boot Application.

### Frontend
Run
```bash
npm i
ng serve
```
in the "searchEngine/frontend" directory.

## Filling in the OpenSearch my_pets index
It will be filled in automatically after starting the aplication.


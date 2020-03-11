# Spring-boot and rabbitmq integration

## Getting Started rabbitmq
Up rabbitmq container:

```bash
docker-compose up -d
```

## Getting Started spring-boot
Up spring-boot:

```bash
mvn spring-boot:run
```

### Json example to message
```json
{
   "nome":"Marcel",
   "idade":31,
   "habilidades":[
      {
         "linguagem":"Java"
      }
   ]
}
```

### TODO 
- Adicionar conexões com o rabbitmq
- Mandar mensagens com problema para uma fila de erros

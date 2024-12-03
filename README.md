# Executar a aplicação

Configurar o Java 21 no seu computador.

Deve fazer o clone para o seu repositório localhost

```bash
$ git clone https://github.com/brgilsonsp/corrida_familia_piba_api.git
```

Acesse o diretório do projeto e execute o seguinte comando que está a aplicação

```bash
$ cd ./corrida_familia_piba_api/sporting-event-race
```

Depois execute o seguinte comando

```bash
$ ./mvnw spring-boot:run
```

#### Mock para a Classificação

Caso queira testar apenas o endpint de classificação, a aplicação pode injetar dados de cronometro, operador, número do atleta e
hora do inicio da corrida.

Para isso, deve passar uma propriedade ```mock``` com o valor ```true```, por exemplo

```bash
$ java -Dmock=true -jar .\sporting-event-race.jar
```
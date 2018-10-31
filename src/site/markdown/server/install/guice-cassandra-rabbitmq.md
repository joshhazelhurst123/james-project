# Guice-Cassandra-Rabbitmq installation guide

## Building

### Requirements

 - Java 8 SDK
 - Docker ∕ ElasticSearch 2.4.6 and Cassandra 3.11.3
 - Maven 3

### Building the artifacts

An usual compilation using maven will produce two artifacts into server/container/guice/cassandra-rabbitmq-guice/target directory:

 * james-server-cassandra-rabbitmq-guice.jar
 * james-server-cassandra-rabbitmq-guice.lib

You can for example run in the base of [this git repository](https://github.com/apache/james-project):

```
mvn clean install
```

## Running

### Requirements

 * Cassandra 3.11.3
 * ElasticSearch 2.4.6
 * RabbitMQ-Management 3.7.7
### James Launch

To run james, you have to create a directory containing required configuration files.

James requires the configuration to be in a subfolder of working directory that is called **conf**. You can get a sample
directory for configuration from
[dockerfiles/run/guice/cassandra-rabbitmq/destination/conf](https://github.com/apache/james-project/tree/master/dockerfiles/run/guice/cassandra-rabbitmq/destination/conf). You might need to adapt it to your needs.

You also need to generate a keystore in your conf folder with the following command:

```bash
$ keytool -genkey -alias james -keyalg RSA -keystore conf/keystore
```

You need to have a Cassandra, ElasticSearch and RabbitMQ instance running. You can either install the servers or launch them via docker:

```bash
$ docker run -d --port 9042:9042 --name=cassandra cassandra:3.11.3
$ docker run -d --port 9200:9200 --port 9300:9300 --name=elasticsearch elasticsearch:2.4.6
$ docker run -d --port 5672:5672 --port 15672:15672 --name=rabbitmq rabbitmq:3.7.7-management
```

Once everything is set up, you just have to run the jar with:

```bash
$ java -Dworking.directory=. -jar target/james-server-cassandra-rabbitmq-guice.jar
```

## Guice-cassandra-rabbitmq-ldap
### Note: this product is not supported yet
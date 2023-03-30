# BatchProcessingModel

Steps to run the project

1.Clone the repository using Git.
2.Download and extract Kafka 3.4.0 from the following link: https://kafka.apache.org/downloads.
3.Follow the steps in https://kafka.apache.org/quickstart to start ZooKeeper and Kafka broker.
4.Open the cloned project using any IDE (such as IntelliJ or VSCode).
5.Build the project using Maven by running the command mvn clean install.
6.The project has two modules: AceTech and Partners.
7.Open two terminals in the IDE.
8.In one terminal, navigate to the Acetech module and start the application using the command mvn spring-boot:run. (Please make sure kafka server is up and running before running the apring boot applications)
9.In the other terminal, navigate to the Partners module and start the application using the command mvn spring-boot:run.
10.Once both applications are up and running, use an API testing tool such as Postman or soapUI to send batches for CRUD operations to the URL localhost:8081/batches/createBatch.
11.A vlaid requestbody for the operation is
[
   {
      "batchid":"PP27644",
      "batchTypeId":"medicine 01",
      "batchTypeDescription":"Panadol",
      "batchExpirationDate":"2024-02-22",
      "batchCount":10
   },
   {
      "batchid":"PP27633",
      "batchTypeId":"medicine 03",
      "batchTypeDescription":"Nurofen",
      "batchExpirationDate":"2024-01-22",
      "batchCount":100
   }
]

12.Once the URI, requestbody is ready, kindly send or invoke the API.
13.A valid response looks like below.
{
    "successfulBatchList": [
        {
            "batchid": "PP27644",
            "batchTypeId": "medicine 01",
            "batchTypeDescription": "Panadol",
            "batchExpirationDate": "2024-02-22",
            "batchCount": 10
        }
    ],
    "failedBatchList": [
        {
            "batchId": "PP27633",
            "message": "Batch ID already exists",
            "statusCode": 409,
            "status": "CONFLICT"
        }
    ],
    "statusCode": 200,
    "message": "Batch Processing completed successfully"
}

14.Once a request is send, the producer in Acetech will send a Batch to the Kafka broker and the consumer of the same topic in Partners will be able read from the topic. Hence in the terminal where partners application was ran, the Batch message will be displayed.

15.Persisted data in Acetech is avilable in Spring boot H2 Db and can be accessed using
  localhost:8081/h2. 

16.To connect to DB, use username - user and leave password as blank. Run SQL queries in BATCH table to view the data.  


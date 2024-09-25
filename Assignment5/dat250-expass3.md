# DAT250: Software Technology Experiment Assignment 5

## Final Report

### Technical Issues Encountered During the Installation and Use of MongoDB and How I Resolved Them

During the installation of MongoDB, I encountered a few technical problems related to package validation and running the `mongod` service.

1. **Package Validation**:
   - **Issue**: The PGP validation of the installation package failed due to an error with the downloaded public key.
   - **Solution**: I reviewed the validation process step by step from the official documentation and re-downloaded the public key following the instructions. Once the package was successfully validated, I proceeded with the installation.

2. **Running the `mongod` Server**:
   - **Issue**: After the installation, when trying to run `mongod`, the server instance did not start properly.
   - **Solution**: I identified that the service wasn't running because it was not enabled to start as a Windows service. I manually started the server from the terminal and added MongoDB to the `PATH` environment variable to facilitate command execution.

---

### Successful Package Validation

The package validation was successful using the instructions from the following link: [https://docs.mongodb.com/manual/tutorial/verify-mongodb-packages/](https://docs.mongodb.com/manual/tutorial/verify-mongodb-packages/).

![Package Validation Screenshot](path/to/screenshot/validation.png)

---

### Relevant Results Obtained During Experiment 1 (CRUD Operations)

## Insert Operation (Create)
I performed the operation of inserting documents into a collection. Below are the relevant screenshots:

- **Screenshot of inserting documents**  
- **Screenshot of querying the collection**  
- **Screenshot of Insert Many**  

Reference link: [Insert Documents](https://docs.mongodb.com/manual/tutorial/insert-documents/)

---

## Query Operation (Read)
To retrieve documents from the collection, the following operation was performed:

- **Screenshot of Insert Many**  
- **Screenshot of Consult Insert (A, D)**  

Reference link: [Query Documents](https://docs.mongodb.com/manual/tutorial/query-documents/)

---

## Update Operation (Update)
I updated a specific document within the collection. Below are the screenshots:

- **Screenshot of InsertUpdate**  
- **Screenshot of InsertManyReplaceOne**  

Reference link: [Update Documents](https://docs.mongodb.com/manual/tutorial/update-documents/)

---

## Delete Operation (Delete)
A specific document was deleted from the collection. The relevant screenshots are:

- **Screenshot of InsertManyDeleteMany**  
- **Screenshot of other commands**  

Reference link: [Remove Documents](https://docs.mongodb.com/manual/tutorial/remove-documents/)

---

## Bulk Write Operations
Several bulk write operations were carried out to insert, update, and delete multiple documents efficiently. The screenshots show the process and results of these operations:

- **Screenshot of bulk write operations**  

Reference link: [Bulk Write Operations](https://docs.mongodb.com/manual/core/bulk-write-operations/)

---

### Example of Experiment 2 Running (Map-Reduce)

#### **Map-Reduce Operation**
For the Map-Reduce experiment, I created a collection of transactions and applied a count by transaction type. The following code was used:

javascript
var mapFunction = function() {
   emit(this.type, 1);
};

var reduceFunction = function(key, values) {
   return Array.sum(values);
};

db.transactions.mapReduce(mapFunction, reduceFunction, { out: "transaction_count" })
This code maps the type field of each transaction document and reduces it by summing the values to get a total count for each transaction type.

![Screenshot of Map-Reduce operation](ruta/a/la/captura/map_reduce.png)

#### **Additional Map-Reduce Operation**
I developed an additional operation to calculate the total amount of transactions by type. The operation was as follows:

var mapFunction = function() {
   emit(this.type, this.amount);
};

var reduceFunction = function(key, values) {
   return Array.sum(values);
};

db.transactions.mapReduce(mapFunction, reduceFunction, { out: "total_amount_by_type" })

This Map-Reduce operation is useful for analyzing the total amounts for each transaction type, which is relevant for financial statistics or audit reports.

![Screenshot of the additional Map-Reduce operation.](ruta/a/la/captura/map_reduce_adicional.png)

---

### Interpretation of the Obtained Collection

The implemented Map-Reduce operation is useful because it provides an efficient way to group and sum amounts based on user-defined categories—in this case, the transaction types. This allows for the easy extraction of aggregated information, which would be much more computationally expensive if done through individual queries or manual data processing.

---

### Outstanding Issues with the Assignment

I encountered no significant unresolved issues. All steps, both installation and operation, were successfully completed.

---

### Conclusion

Through this experiment, I gained practical experience using MongoDB to perform basic CRUD operations and apply the Map-Reduce technique for data aggregation. Despite the initial technical problems, they were successfully resolved, allowing the assignment to be completed satisfactorily.

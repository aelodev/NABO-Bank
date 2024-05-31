 <h1 style="display: flex; justify-content: center; align-items: center">NABO Bank</h1>

<img alt="Logo" height="200" src="/src/media/logo.png" width="200" style="display: flex; margin: auto"/>

## Description

NABO Bank is a simple banking application where you can deposit, transfer, see the history of your transactions.
It is a simple project to practice the concepts of Java Swing GUI, connection with Database and writing files.

## Features

- Deposit money
- Transfer money
- See the history of transactions
- Save the history of transactions in a file

## Technologies

[![Static Badge](https://img.shields.io/badge/Java-red?style=for-the-badge&logo=openjdk)]()

[![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)]()

[![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)]()

[![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)]()

## How to use

1. Create a MySQL database named ``banco`` in XAMMP
2. Create a table with the following structure, it will be used to store the users of the application:

```sql
CREATE TABLE users (
    id INT PRIMARY KEY,
    usuario VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    saldo DOUBLE DEFAULT 0
);

/*
 Insert a user
 */

INSERT INTO users (id, usuario, password, nombre, apellidos, saldo)
VALUES (1, 'username', 'password', 'First Name', 'Last Name', 100.0);
```

3. Create a table ``history`` with the following structure:

```sql
   CREATE TABLE historial (
    id INT PRIMARY KEY,
    accion VARCHAR(255) NOT NULL,
    usuario VARCHAR(255) NOT NULL,
    cantidad VARCHAR(255) NOT NULL,
    fecha DATE NOT NULL,
    usuario_id INT NOT NULL
   );
```

4. Clone the repository
5. Open the project in your IDE
6. Run the project
7. Log in with the user you created in the database
8. Try the features

## Screenshots

![Login](/src/media/screenshots/login.png)

![General](/src/media/screenshots/general.png)

![Transfer](/src/media/screenshots/pay.png)

![History](/src/media/screenshots/history.png)

![Deposit](/src/media/screenshots/charge.png)

![Export](/src/media/screenshots/export.png)

## License

This project is licensed under the GPL 3.0 License - see the [LICENSE](LICENSE) file for details.

## Developed by

- This project was developed by Alejandro Lucena, aka Lucenabo.

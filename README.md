# SentraX - Real-Time Transaction Fraud Detection (Java Web)
Servlets 4.0 + JSP + JDBC + MySQL 8. Rule-based, explainable risk score (0-100): Safe 0-34, Suspicious 35-69, Fraud 70-100.

## Run
1. MySQL: run `database/schema.sql`, then `database/data.sql`.
2. Copy `src/main/resources/db.properties.example` to `db.properties` and set your MySQL user/password.
3. Build: `mvn clean package` (JDK 11+). Deploy `target/sentrax.war` on **Tomcat 9** (not 10+).
4. Open `http://localhost:8080/sentrax/` and sign in with `analyst` / `analyst123`.

## Demo
- Normal: Asha, Rs 800, Delhi, device ASHA-PHONE, 2 PM -> Safe.
- Fraud: Asha, Rs 40000, Mumbai, device NEW-DEV-1, time 02:30 -> Fraud, alert created.

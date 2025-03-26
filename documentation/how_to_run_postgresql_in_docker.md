# How to Run PostgreSQL in Docker

Execute this command in project root:
`docker-compose up -d`

Verify it's running
`docker ps`

Verify the Database Connection
`docker exec -it torrestrace_postgres psql -U torrestrace -d torrestrace`
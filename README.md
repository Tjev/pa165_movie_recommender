# PA165 Movie Recommender
For the description, use case and class diagrams of the project, see the project wiki [here](https://github.com/Tjev/pa165_movie_recommender/wiki).

## REST API

Run `mvn clean install && cd movie-recommender-rest && mvn cargo:run` for REST API deployment.

The Swagger documentation is available at [http://localhost:8080/pa165/rest/swagger-ui.html](http://localhost:8080/pa165/rest/swagger-ui.html).

Below there are described examples of the CURL commands for each endpoint:

### /persons

#### [POST] /persons/create
`curl -X POST -i -H "Content-Type: application/json" --data '{"name": "Mads Mikkelsen", "bio": "He is cool.", "dateOfBirth": "1999-01-01"}' http://localhost:8080/pa165/rest/persons/create`

#### [GET] /persons/{id}
`curl -X GET -i http://localhost:8080/pa165/rest/persons/1`

#### [GET] /persons/find-by-name
`curl -X GET -i http://localhost:8080/pa165/rest/persons/find-by-name?name=Mads%20Mikkelsen`
  
#### [PUT] /persons/update
`curl -X PUT -i -H "Content-Type: application/json" --data '{"id": "1", "name": "Mads Mikkelsen", "bio": "He is not that cool.", "dateOfBirth": "1999-01-01"}' http://localhost:8080/pa165/rest/persons/update`

#### [DELETE] /persons/remove
`curl -X DELETE -i -H "Content-Type: application/json" --data '{"id": "1"}' http://localhost:8080/pa165/rest/persons/remove`

### /movies

#### [POST] /movies/create
`curl -X POST -i -H "Content-Type: application/json" --data '{"title": "Dune", "releaseYear": "2021-10-01", "genres": ["SCIFI"]}' http://localhost:8080/pa165/rest/movies/create`

#### [GET] /movies/{id}
`curl -X GET -i http://localhost:8080/pa165/rest/movies/1`

#### [GET] /movies/find-by-title
`curl -X GET -i http://localhost:8080/pa165/rest/movies/find-by-title?title=Dune`

#### [PUT] /movies/update
`curl -X PUT -i -H "Content-Type: application/json" --data '{"id": "1", "title": "Dune", "releaseYear": "2021-10-01", "genres": ["SCIFI", "ACTION"]}' http://localhost:8080/pa165/rest/movies/update`

#### [DELETE] /movies/remove
`curl -X DELETE -i -H "Content-Type: application/json" --data '{"id": "1"}' http://localhost:8080/pa165/rest/movies/remove`

### /ratings

#### [POST] /ratings/create
`curl -X POST -i -H "Content-Type: application/json" --data '{"user":{"id":1},"movie":{"id":1},"originality":1,"soundtrack":1,"narrative":1,"cinematography":1,"depth":1}' http://localhost:8080/pa165/rest/ratings/create`

#### [PUT] /ratings/update
`curl -X PUT -i -H "Content-Type: application/json" --data '{"id":1,"user":{"id":1},"movie":{"id":1},"originality":5,"soundtrack":5,"narrative":5,"cinematography":5,"depth":5}' http://localhost:8080/pa165/rest/ratings/update`

#### [DELETE] /ratings/remove
`curl -X DELETE -i -H "Content-Type: application/json" --data '{"id":1}' http://localhost:8080/pa165/rest/ratings/remove`

#### [GET] /ratings/{id}
`curl -X GET -i http://localhost:8080/pa165/rest/ratings/1`

#### [GET] /ratings/find-by-user
`curl -X GET -i -H "Content-Type: application/json" --data '{"id":1}' http://localhost:8080/pa165/rest/ratings/find-by-user`

#### [GET] /ratings/find-by-movie
`curl -X GET -i -H "Content-Type: application/json" --data '{"id":1}' http://localhost:8080/pa165/rest/ratings/find-by-movie`

#### [GET] /ratings/overall-score
`curl -X GET -i -H "Content-Type: application/json" --data '{"id":1}' http://localhost:8080/pa165/rest/ratings/overall-score`

### /users

#### [PUT] /users/create
`curl -X PUT -i -H "Content-Type: application/json" --data '{"username": "TomWiseau1", "emailAddress": "tom.wiseau@gmail.com", "password": "OhHiMark"}' http://localhost:8080/pa165/rest/users/register`

#### [GET] /users/{id}
`curl -X GET -i http://localhost:8080/pa165/rest/users/1`
  
#### [GET] /users/find-by-username
`curl -X GET -i http://localhost:8080/pa165/rest/users/find-by-username?username=TomWiseau1`
  
#### [GET] /users/find-by-email
`curl -X GET -i http://localhost:8080/pa165/rest/users/find-by-mail?emailAddress=tom.wiseau%40gmail.com`
  
#### [POST] /users/auth
`curl -X POST -i -H "Content-Type: application/json" --data '{"emailAddress": "tom.wiseau@gmail.com", "password": "OhHiMark"}' http://localhost:8080/pa165/rest/users/auth`

#### [GET] /users/is-admin
`curl -X GET -i -H "Content-Type: application/json" --data '{"id": "1"}' http://localhost:8080/pa165/rest/users/is-admin`

#### [GET] /users/is-disabled
`curl -X GET -i -H "Content-Type: application/json" --data '{"id": "1"}' http://localhost:8080/pa165/rest/users/is-disabled`

#### [POST] /users/disable
`curl -X POST -i -H "Content-Type: application/json" --data '{"id": "1"}' http://localhost:8080/pa165/rest/users/disable`
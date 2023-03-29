## Acceptance Criteria

- An API consumer can search for books given a title
- An API consumer can post a review and a rating for a specific book
- An API consumer can get the details and the average rating of a specific book
- Every API service handles API errors

## Bonus Objectives

- Handle pagination of the search service
- Caching mechanism for book details
- API service that returns the top N books based on their average rating
- API service that, given a book id, returns its average rating per month

## Build steps

```bash
1. docker build --no-cache -f moro-api.Dockerfile -t geokall/moro-api:latest .
2. docker push geokall/moro-api:latest
```

## How to run

```bash
1. Install docker-compose (avoid this step, if it is already installed)
2. docker-compose up
```

## Some key points:

- Dev environment runs based on pushed docker image
- Docker environment assumes db is running using docker-compose
- Minor tests have been implemented (H2 could have been used too)

## OpenAPI available:

Link available only if server is running:
[available endpoints](http://localhost:8080/swagger-ui/index.html)
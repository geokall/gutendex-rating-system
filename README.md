## Acceptance Criteria

- An API consumer can search for books given a title
- An API consumer can post a review and a rating for a specific book
- An API consumer can get the details and the average rating of a specific book
- The services should gracefully handle API errors

## Bonus Objectives

- Handle pagination of the search service.
- Implement a caching mechanism for book details
- Create a service that returns the top N books based on their average rating
- Create a service that, given a book id, returns its average rating per month
- Provide a high-level system diagram of how you would deploy such a service in a scalable way

## How to run

```bash
1. Install docker-compose (avoid this step, if it's already installed')
2. docker-compose up
```

## Some key points:

- Dev environment runs based on pushed docker image
- Docker environment assumes db is running using docker-compose
- Minor tests have been implemented (H2 could have been used too)

## OpenAPI available:

Link available only if server is running:
[available endpoints](http://localhost:8080/swagger-ui/index.html)
# Requirements Summary

## Data requirements:
### Must-have

- An anomaly has unique name, a category and a freeform description.
- An anomaly can be associated with any number of observations.
- An observation includes a date, location, and a freeform description.
- Spring data is used to persist all data.

### Should-have
- Utilises constraint annotations to specify e.g. mandatory values and string lengths ?

## General API requirements
### Must-have
- API is implemented using Spring MVC.
- API returns data in JSON format.
- API endpoints are designet according to RESTful principles.
- Able to fetch list of all anomalies, info about specific anomaly, add anomaly, add observation for specific anomaly.

### Should-have
- Update anomalies and observations.
- Delete anomalies and observations.

## Security requirements
### Must-have
- Spring Security is used to secure the application.
- Fetch API endpoints are accessible to anyone.
- Add/update/delete API endpoints are only available to authenticated users.
- Using JWT
- Hard-coded user accounts.

## Documentation requirements
### Must-have
- Using generated documentation.
- summary of must-have/should-have/nice-to-have functions.

### Should-have
- Documentation is generated from the OpenAPI Specification with Swagger UI.

## Testing requirements
### Must-have
- Using interactive API documentation.

## Delivery requirements
### Must-have
- All code and documentation is available in a single GitHub repository.

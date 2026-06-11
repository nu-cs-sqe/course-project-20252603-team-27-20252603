![Gradle Build](https://github.com/nu-cs-sqe/course-project-20252603-team-27-20252603/actions/workflows/main.yml/badge.svg)

[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23625230)
# PROJECT NAME
Chess

## Contributors
- Olivia Mofus
- Jerome Bizimana
- Muye Chen
- Rishi Ramaiya

## Dependencies
- JDK 11
- JUnit 5.10
- Gradle 8.10
- EasyMock 5.2.0
- SpotBugs Annotations 4.8.6

## Acknowledgements
GUI example code given by professor

## Test and Code Quality
### Tools
- JUnit 5.10
- Gradle 8.10
- SpotBugs 6.0.25
- Pitest 1.19.0
- jacoco 0.8.14
- checkstyle 10.12.5

### Test Coverage
- 100% for the branch coverage
### Mutation Testing
- Line 390 in Game.java can't be killed since if to.getCol() = from.getCol(), the methods will return false before this line. This line is used to determine which direction the king goes
- Total mutation coverage is 464/469, test strength is 464/469, line coverage is 100%
### checkstyle
- 100% pass for the checkstyle

CS 2340
Syllabus
Policies
Schedule
Resources
Project
Summer 2014 Project: Trip Planner
Problem Description
You are taking a trip to a new city and would like to plan your itinerary -- where to eat, what to see and do -- based on where you're staying. You want a single application in which you can specify the times you're available for meals and activities, your preferences for ratings and cost, and your transportation mode, and have the software give you a trip itinerary based on your preferences and the restaurants and attractions within a specified distance from your accomodations.

Software Requirements
Definitions, Acronyms, and Abbreviations
Lodging: a place in which the user lives or stays temporarily. For trip planning, it is the geographic location from which trip plans originate.
Restaurant: a place where a person can eat a meal. A restaurant has, at least, a name, description, food type, cost range, hours of operation during which customers can receive service, a customer rating, and customer reviews.
Attraction: a place at which a person can engage in a leisure activity, such as a museum, a park, or a skating rink. An attraction has, at least, a name, description, activity category (sports/arts/etc.), cost, hours of operation, a customer rating, and customer reviews.
Place: a restaurant or attraction.
Customer rating: a summary statistic of the rating a customer gives a restaurant or attraction. A rating is some number on a scale, and optionally a descriptive name for the rating such as "stars" or "chilli peppers". For example, a customer can give a restaurant "4 out of 5 stars." 4 is the rating, 5 is the scale, and stars is the descriptive name.
Customer Review: a textual (and optionally media-including) description of a customer's experience with a restaurant or attraction.
Geographic location: a street address that fixes the position of a place of lodging, restaurant or attraction on a Google map. Optionally a latitude/longitude pair for places not accessible by road.
Route: a path from one geographic location to another via some mode of transportation.
Mode of transportation: the means by which a person transports themself from one location to another, such as walking, bicycling, bus, or car.
Overall Description
The system will provide users with trip planning information. The user will specify a place of lodging, a time frame or series of time frames, and the kinds of restaurants or attractions the user would like to visit during those time frames which the system will use to provide an itinerary that includes each of the restaurants or attractions the user asked to include, the routes between each place in the itinerary, and target times for beginning transit routes, arrivals, and departures. The restaurants and attractions chosen by the system will be open for business during the chosen times and meet the constraints specified by the user for type, cost, and rating. The system will also present to the user summary and review infomration retrieved from various web services such as Yelp and report to the user any social media connections that have check-ins or reviews of any of the places in the itinerary. The system will allow a user to store itineraries to be viewed later.

User Characteristics

Users of the system will be comfortable using interactive browser-based software, be able to read English language text, and understand simple diagrams and pictures as might be found in popular magazines or newspapers.

Constraints

Get geophraphic data from Google Maps via Google Maps Java API
Get reviews, cost, and business hours from Yelp via Yelp REST API, or Google REST API.
Get social information, such as which of your Facebook friends like places, have checked in on Facebook or Foursquare, etc., via their respective web service APIs
The user interface must run in a web browser.
Specific Requirements
Functional Requirements

Account Management

R1: The system shall provide a means for the user to create an account by selecting a user name that is unique within the system, and a password.
R2: The System shall provide a means for the user to log in to the application using their user name and password so that the user is presented with customized information.
R3: The system shall provide a means for the user to enter social media contact credentials on a per session basis so that the system can retrieve information from social media web service APIs on the user's behalf.
R4: The system shall provide a means for the user to update their login credentials.
R5: The system shall provide a means for the user to update their social media credentials.
R6: The system shall store the user's login credentials on the server.
Place Information

R7: The system shall provide a means for a user to specify a central location for an itinerary.
R8: The system shall provide a means for the user to specify a mode of transportation to be used for planning distances, times, and routes in the itinerary.
R9: The system shall provide a means for the user to specify preferences for places, such as price range, food types, minimum ratings, and so on as described in Definitions.
R10: The system shall provide a means for the user to specify time frames during which places must be open for business.
R11: The system shall provide the user with a list of places within travel distance that meet the user's preferences and constraints.
R12: The system shall provide a means for the user to view more detailed infomation about the places reported in R11
Itineraries

R13: The system shall provide a means for the user to add places reported in R11 to an itinerary.
R14: The system shall provide a means for the user to remove places from an itinerary.
R15: The system shall provide a means for the user to order the places in an itinerary.
R16: The system shall create a travel route for an ordered itinerary based on time frames and transportation mode.
R17: The system shall store user itineraries in a database.
R18: The system shall allow the user to retrieve itineraries from the database.
Social Media Integration

R19: The system shall report to the user any friends that have checked-in a place reported in R11.
R20: The system shall report to the user any reviews or ratings posted by friends for a place reported in R11.
R21: The system shall allow the user to post a trip itinerary to the user's social media accounts.
R22: The system shall provide the user with links to sites to post reviews.
Milestones and Grading
Individual Part
Each team member will receive a score, on a 100 point scale, based on both group and individual work. At the end of each milestone, each team member will be scored individually on the following items:

(20 pts) git pull gets individual's master and topic branches for the milestone (not necessarily the integration manager's master branch). git branch --list lists branches.
(20 pts) ant clean deploy runs successfully
(10 pts) after ant clean deploy and $CATALINA_HOME/bin/startup.sh web application runs in browser at http://localhost:8080/${app.name}``
These individually scored items ensure that each team member is keeping up with the project. If an individual team member does not keep up at all, then their max possible score for a milestone would be 50 out of 100.

Team Part
For each milestone, each team will be scored as a group based on the items listed below for each iteration. Each team member gets the same number of points for these items.

M1: Account Management, R1 -- R6

(10 pts) Clean code
(5 pts) R1: Account creation
(5 pts) R2: Log in
(5 pts) R3: Per-session social media credentials
(5 pts) R4: Updating login credentials
(5 pts) R5: Updating social media credentials
(5 pts) R6: Storing system login credentials
(10 pts) M1 tag present in integration manager's repo
M2: Place Information, R7 -- R12

(10 pts) Clean code
(5 pts) R7: Central location
(5 pts) R8: Transportation mode
(5 pts) R9: Place Preferences
(5 pts) R10: Time constraints
(5 pts) R11: Listing matching places
(5 pts) R12: Viewing place information
(10 pts) M2 tag present in integration manager's repo
M3: Itineraries, R13 -- R18

(10 pts) Clean code
(5 pts) R13: Adding places to itineraries
(5 pts) R14: Removing places from itineraries
(5 pts) R15: Ordering places initineraries
(5 pts) R16: Creating travel routes between places in itineraries
(5 pts) R17: Storing itineraries in database
(5 pts) R18: Retrieving itineraries from database
(10 pts) M3 tag present in integration manager's repo
M4: Social Media Integration, R19 -- R22

(10 pts) Clean code
(10 pts) R19: Reporting friend check-ins at places
(10 pts) R20: Reporting friend reviews of places
(5 pts) R21: Posting itineraries to social media accounts
(5 pts) R22: Linking to social media review sites
(10 pts) M4 tag present in integration manager's repo
Bonus Points

Teams can earn bonus boints in the following categories. Points will be added to the final milestone score.

User Interface/View Layer

Use Twitter Bootstrap (5 points)
Write Custom CSS (10 points)
Add JavaScript Interactivity (10 points)
Features

Budget: allow user to specify a budget for an itinerary, present user with budget-respecting options and track budget in itinerary (10 points)
Muli-city: allow user to create multi-city, multi-day itineraries (essentially, multiple "central locations") (10 points)
Security

User credential security (unrecoverable passwords) (5 points)
https support using self-signed certificate (5 points)
Security audit report (10 points)
Development Practices

Integrate checkstyle into biuld process (5 points)
Git hook to reject commits that don't pass checkstyle (5 points)
Library dependency management (e.g., Maven or Ivy) (5 points)
Miscellaneous

Present project to class (5 points)

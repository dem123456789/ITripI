# ItripI

ItripI is a trip planner web application using. It uses Goolge and Yelp API to filter possible destination like restaurants and hotels based on the schedule and preferences of user.

## Introduction

1. It uses MVC model to structure the project.
2. Front-end mainly uses HTML and Javascript and Back-end uses Java
3. I fell that the authentication process of Google and Yelp API is tedious
4. This is the first time I build a web application

## Contributing

1. Fork
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request


## Credits

*Fan Chen  
Enmao Diao  
Jonathan Hunter  
Penlin Li  
Yuying Zhang*

<h2>Problem Description</h2>

<p>You are taking a trip to a new city and would like to plan your itinerary -- where to eat, what to see and do -- based on where you're staying.  You want a single application in which you can specify the times you're available for meals and activities, your preferences for ratings and cost, and your transportation mode, and have the software give you a trip itinerary based on your preferences and the restaurants and attractions within a specified distance from your accomodations.</p>

<h2>Software Requirements</h2>

<div class="section" id="definitions-acronyms-and-abgreviations">
<h3>Definitions, Acronyms, and Abbreviations</h3>

<ul>
<li>Lodging: a place in which the user lives or stays temporarily.  For trip planning, it is the geographic location from which trip plans originate.</li>
<li>Restaurant: a place where a person can eat a meal.  A restaurant has, at least, a name, description, food type, cost range, hours of operation during which customers can receive service, a customer rating, and customer reviews.</li>
<li>Attraction: a place at which a person can engage in a leisure activity, such as a museum, a park, or a skating rink. An attraction has, at least, a name, description, activity category (sports/arts/etc.), cost, hours of operation, a customer rating, and customer reviews.</li>
<li>Place: a restaurant or attraction.</li>
<li>Customer rating: a summary statistic of the rating a customer gives a restaurant or attraction.  A rating is some number on a scale, and optionally a descriptive name for the rating such as "stars" or "chilli peppers".  For example, a customer can give a restaurant "4 out of 5 stars."  4 is the rating, 5 is the scale, and stars is the descriptive name.</li>
<li>Customer Review: a textual (and optionally media-including) description of a customer's experience with a restaurant or attraction.</li>
<li>Geographic location: a street address that fixes the position of a place of lodging, restaurant or attraction on a Google map.  Optionally a latitude/longitude pair for places not accessible by road.</li>
<li>Route: a path from one geographic location to another via some mode of transportation.</li>
<li>Mode of transportation: the means by which a person transports themself from one location to another, such as walking, bicycling, bus, or car.</li>
</ul>

<h3>Overall Description</h3>

<p>
    The system will provide users with trip planning information.  The user will specify a place of lodging, a time frame or series of time frames, and the kinds of restaurants or attractions the user would like to visit during those time frames which the system will use to provide an itinerary that includes each of the restaurants or attractions the user asked to include, the routes between each place in the itinerary, and target times for beginning transit routes, arrivals, and departures.  The restaurants and attractions chosen by the system will be open for business during the chosen times and meet the constraints specified by the user for type, cost, and rating.  The system will also present to the user summary and review infomration retrieved from various web services such as Yelp and report to the user any social media connections that have check-ins or reviews of any of the places in the itinerary.  The system will allow a user to store itineraries to be viewed later.
</p>

<h4>User Characteristics</h4>

<p>Users of the system will be comfortable using interactive browser-based software, be able to read English language text, and understand simple diagrams and pictures as might be found in popular magazines or newspapers.</p>

<h4>Constraints</h4>

<ul>
<li>Get geophraphic data from Google Maps via Google Maps Java API</li>
<li>Get reviews, cost, and business hours from Yelp via Yelp REST API, or Google REST API.</li>
<li>Get social information, such as which of your Facebook friends like places, have checked in on Facebook or Foursquare, etc., via their respective web service APIs</li>
<li>The user interface must run in a web browser.</li>
</ul>

<h3>Specific Requirements</h3>

<h4>Functional Requirements</h4>

<h5>Account Management</h5>

<ul>
<li id="R1">R1: The system shall provide a means for the user to create an account by selecting a user name that is unique within the system, and a password.</li>
<li id="R2">R2: The System shall provide a means for the user to log in to the application using their user name and password so that the user is presented with customized information.</li>
<li id="R3">R3: The system shall provide a means for the user to enter social media contact credentials on a per session basis so that the system can retrieve information from social media web service APIs on the user's behalf.</li>
<li id="R4">R4: The system shall provide a means for the user to update their login credentials.</li>
<li id="R5">R5: The system shall provide a means for the user to update their social media credentials.</li>
<li id="R6">R6: The system shall store the user's login credentials on the server.</li>
</ul>

<h5>Place Information</h5>

<ul>
<li id="R7">R7: The system shall provide a means for a user to specify a central location for an itinerary.</li>
<li id="R8">R8: The system shall provide a means for the user to specify a mode of transportation to be used for planning distances, times, and routes in the itinerary.</li>
<li id="R9">R9: The system shall provide a means for the user to specify preferences for places, such as price range, food types, minimum ratings, and so on as described in Definitions.</li>
<li id="R10">R10: The system shall provide a means for the user to specify time frames during which places must be open for business.</li>
<li id="R11">R11: The system shall provide the user with a list of places within travel distance that meet the user's preferences and constraints.</li>
<li id="R12">R12: The system shall provide a means for the user to view more detailed infomation about the places reported in R11</li>
</ul>


<h5>Itineraries</h5>

<ul>
<li id="R13">R13: The system shall provide a means for the user to add places reported in R11 to an itinerary.</li>
<li id="R14">R14: The system shall provide a means for the user to remove places from an itinerary.</li>
<li id="R15">R15: The system shall provide a means for the user to order the places in an itinerary.</li>
<li id="R16">R16: The system shall create a travel route for an ordered itinerary based on time frames and transportation mode.</li>
<li id="R17">R17: The system shall store user itineraries in a database.</li>
<li id="R18">R18: The system shall allow the user to retrieve itineraries from the database.</li>
</ul>

<h5>Social Media Integration</h5>

<ul>
<li id="R19">R19: The system shall report to the user any friends that have checked-in a place reported in R11.</li>
<li id="R20">R20: The system shall report to the user any reviews or ratings posted by friends for a place reported in R11.</li>
<li id="R21">R21: The system shall allow the user to post a trip itinerary to the user's social media accounts.</li>
<li id="R22">R22: The system shall provide the user with links to sites to post reviews.</li>
</ul>



<h2>Milestones and Grading</h2>

<div class="section" id="individual-part">
<h3>Individual Part</h3>
<p>Each team member will receive a score, on a 100 point scale, based on both group and individual work.  At the end of each milestone, each team member will be scored individually on the following items:</p>
<ul class="simple">
<li>(20 pts) <tt class="docutils literal">git pull</tt> gets individual's master and topic branches for the milestone (not necessarily the integration manager's master branch).  <tt class="docutils literal">git branch <span class="pre">--list</span></tt> lists branches.</li>
<li>(20 pts) <tt class="docutils literal">ant clean deploy</tt> runs successfully</li>
<li>(10 pts) after <tt class="docutils literal">ant clean deploy</tt> and <tt class="docutils literal">$CATALINA_HOME/bin/startup.sh</tt> web application runs in browser at <a class="reference external" href="http://localhost:8080">http://localhost:8080</a>/${app.name}``</li>
</ul>
<p>These individually scored items ensure that each team member is keeping up with the project.  If an individual team member does not keep up at all, then their max possible score for a milestone would be 50 out of 100.</p>
</div>
<div class="section" id="team-part">
<h3>Team Part</h3>
<p>For each milestone, each team will be scored as a group based on the items listed below for each iteration.  Each team member gets the same number of points for these items.</p>
</div>

<h4>M1: Account Management, R1 -- R6</h4>
<ul class="simple">
<li>(10 pts) Clean code</li>
<li>(5 pts) <a class="reference internal" href="#R1">R1</a>: Account creation</li>
<li>(5 pts) <a class="reference internal" href="#R2">R2</a>: Log in</li>
<li>(5 pts) <a class="reference internal" href="#R3">R3</a>: Per-session social media credentials</li>
<li>(5 pts) <a class="reference internal" href="#R4">R4</a>: Updating login credentials</li>
<li>(5 pts) <a class="reference internal" href="#R5">R5</a>: Updating social media credentials</li>
<li>(5 pts) <a class="reference internal" href="#R6">R6</a>: Storing system login credentials</li>

<li>(10 pts) M1 tag present in integration manager's repo</li>
</ul>

<h4>M2: Place Information, R7 -- R12</h4>
<ul class="simple">
<li>(10 pts) Clean code</li>
<li>(5 pts) <a class="reference internal" href="#R1">R7</a>: Central location</li>
<li>(5 pts) <a class="reference internal" href="#R2">R8</a>: Transportation mode</li>
<li>(5 pts) <a class="reference internal" href="#R3">R9</a>: Place Preferences</li>
<li>(5 pts) <a class="reference internal" href="#R4">R10</a>: Time constraints</li>
<li>(5 pts) <a class="reference internal" href="#R4">R11</a>: Listing matching places</li>
<li>(5 pts) <a class="reference internal" href="#R4">R12</a>: Viewing place information</li>
<li>(10 pts) M2 tag present in integration manager's repo</li>
</ul>

<h4>M3: Itineraries, R13 -- R18</h4>
<ul class="simple">
<li>(10 pts) Clean code</li>
<li>(5 pts) <a class="reference internal" href="#R1">R13</a>: Adding places to itineraries</li>
<li>(5 pts) <a class="reference internal" href="#R2">R14</a>: Removing places from itineraries</li>
<li>(5 pts) <a class="reference internal" href="#R3">R15</a>: Ordering places initineraries</li>
<li>(5 pts) <a class="reference internal" href="#R4">R16</a>: Creating travel routes between places in itineraries</li>
<li>(5 pts) <a class="reference internal" href="#R4">R17</a>: Storing itineraries in database</li>
<li>(5 pts) <a class="reference internal" href="#R4">R18</a>: Retrieving itineraries from database</li>
<li>(10 pts) M3 tag present in integration manager's repo</li>
</ul>

<h4>M4: Social Media Integration, R19 -- R22</h4>
<ul class="simple">
<li>(10 pts) Clean code</li>
<li>(10 pts) <a class="reference internal" href="#R1">R19</a>: Reporting friend check-ins at places</li>
<li>(10 pts) <a class="reference internal" href="#R2">R20</a>: Reporting friend reviews of places</li>
<li>(5 pts) <a class="reference internal" href="#R3">R21</a>: Posting itineraries to social media accounts</li>
<li>(5 pts) <a class="reference internal" href="#R4">R22</a>: Linking to social media review sites</li>
<li>(10 pts) M4 tag present in integration manager's repo</li>
</ul>

<h4>Bonus Points</h4>

<p>Teams can earn bonus boints in the following categories.  Points will be added to the final milestone score.</p>

<h5>User Interface/View Layer</h5>

<ul>
<li>Use Twitter Bootstrap (5 points)</li>
<li>Write Custom CSS (10 points)</li>
<li>Add JavaScript Interactivity (10 points)</li>
</ul>

<h5>Features</h5>

<ul>
<li>Budget: allow user to specify a budget for an itinerary, present user with budget-respecting options and track budget in itinerary (10 points)</li>
<li>Muli-city: allow user to create multi-city, multi-day itineraries (essentially, multiple "central locations") (10 points)</li>
</ul>

<h5>Security</h5>

<ul>
<li>User credential security (unrecoverable passwords) (5 points)</li>
<li>https support using self-signed certificate (5 points)</li>
<li>Security audit report (10 points)</li>
</ul>

<h5>Development Practices</h5>

<ul>
<li>Integrate checkstyle into biuld process (5 points)</li>
<li>Git hook to reject commits that don't pass checkstyle (5 points)</li>
<li>Library dependency management (e.g., Maven or Ivy) (5 points)</li>
</ul>

<h5>Miscellaneous</h5>

<ul>
<li>Present project to class (5 points)</li>
</ul>

## License

The MIT License (MIT)

Copyright (c) [2015] [Enmao Diao, Fan Chen, Penlin Li, Yuying Zhang, Jonathan Hunter]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

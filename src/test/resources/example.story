Bid Points Calculation Story

Narrative:
In order to communicate effectively to the business some functionality
As a development team
I want to use Behaviour-Driven Development

Scenario:  Bid point calculation second bid
Given empty database
And a user user1@mail.com
And a user user2@mail.com
And a multi user bid with id 1 where the creator is user1@mail.com and partner is user2@mail.com at 03/01/2014 
And a bid with id 2 where the creator is user1@mail.com at 04/01/2014
And the current date is 05/01/2014
When the system calculates the bids
Then the bid with id 1 is going to have 1 points
Then the bid with id 2 is going to have 3 points

Scenario: Bid point calculation first bid
Given empty database
And a user user1@mail.com
And a bid with id 1 where the creator is user1@mail.com at 04/01/2014
And the current date is 05/01/2014
When the system calculates the bids
Then the bid with id 1 is going to have 2 points

Scenario: Bid point calculation second bid
Given empty database
And a user user1@mail.com
And a bid with id 1 where the creator is user1@mail.com at 04/01/2014
And a bid with id 2 where the creator is user1@mail.com at 03/01/2014
And the current date is 05/01/2014
When the system calculates the bids
Then the bid with id 2 is going to have 2 points
Then the bid with id 1 is going to have 4 points

Scenario: Bid point calculation second bid
Given empty database
And a user user1@mail.com
And a user user2@mail.com
And a multi user bid with id 1 where the creator is user1@mail.com and partner is user2@mail.com at 04/01/2014
And the current date is 05/01/2014
When the system calculates the bids
Then the bid with id 1 is going to have 1 points

Scenario: Bids which are 4 weeks earlier are not counted
Given empty database
And a user user1@mail.com
And a bid with id 1 where the creator is user1@mail.com at 06/01/2014
And a bid with id 2 where the creator is user1@mail.com at 08/01/2014
And a bid with id 3 where the creator is user1@mail.com at 15/01/2014
And a bid with id 4 where the creator is user1@mail.com at 22/01/2014
And a bid with id 5 where the creator is user1@mail.com at 29/01/2014
And the current date is 29/01/2014
When the system calculates the bids
Then the bid with id 1 is going to have 2 points
Then the bid with id 2 is going to have 4 points
Then the bid with id 3 is going to have 6 points
Then the bid with id 4 is going to have 8 points
Then the bid with id 5 is going to have 10 points
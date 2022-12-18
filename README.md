# Poll-Voting-App
Poll Voting App for my Android Programming course at the Faculty Of Electrical Engineering and Information Technologies.

The app is written in Java, and it has a register/login page, connected to a "Users" database using firebase, where every user is registered.
One user has admin access, and can create polls for other users to vote. The polls are also saved in a firebase rlt database, and are then displayer to the users.
The polls are displayed using a RecyclerView, and are limited with time, meaning they will expire after some time given by the admin upon creation of that poll.
The polls have a dynamic amount of options, and after the timer for the poll runs out, the users can see their chosen option on said poll by going to the MyVotesActivity.
Meanwhile, the admin can see the votes in realtime, which are displayed in a RecyclerView in the AdminActivity. The votes are also kept in a firebase database.
Besides the Users, Polls and Votes database, there is also a database for the timers of the polls, which is also a firebase database.

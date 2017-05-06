# Server - Client Protocol

## Case: client connects to a server

* Send a string with the name of the client
* Server responds with the test message that includes client name

## Case: client request current world status

* Client sends a string "GET CELLS".
* Server responds with a json string of the following format:

[
  {
    "Vector" : [1, 2, 3]
  }
]
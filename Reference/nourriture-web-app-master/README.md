Nourriture Web App
==================

Frontend of the Nourriture. The application depends on our backend "Nourriture-platform".

#### Install and start local instance
Execute the following in the root of your repository clone:

    npm install                         # Install dependencies
    sudo npm install -g http-server     # Install web-server (if you don't have it)
    http-server .                       # Start web-server

This will start a basic local HTTP-server at "http://localhost:8080" serving the content. In production it is of course hosted using Nginx but this setup is sufficient for rapid local development.

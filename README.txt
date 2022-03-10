Restaurant api

It requires to have installed for running this application.
    - maven
    - docker

1. Get into the directory "restaurant" using any console app.
2. Run the command: mvn clean package.
3. Run the command: docker-compose up -d --build
4. Access the url: http://localhost:8000/restaurant/swagger-ui.html
5. Access Menu Item Controller (menu-item-controller).

	All operations must be done using it. There are
	3 operations: "List all Menu Items", "Return a Menu Item by its id" and "Change Status Menu Item.".

	1. List all Menu Items: return all Menu Items inserted in the database. The Menu Item were inserted as the task definition describes.
	Examples: 1 - Hamburguer - 1
			  2 - Coke		 - 1

  	2. Return a Menu Item by its id: return a Menu Item when an id is sent as a parameter.

	3. Change Status Menu Item: this is the main function of this app. It requires a Menu Item id and a Status id. If everything is right, this function will change the status of Menu Item, otherwise an exception will be thrown. After this the result of this function can be seen in the function 2 (Return a Menu Item by its id).
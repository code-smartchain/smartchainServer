# Smartchain Server
The BackendServer connecting the WebUI with the smartchainhApp

Repository contains the UI and the hApp for the SmartChain solution based in Holochain.

## Routes

-- To be added --

## Purpose

This Service is a SpringBoot Endpoint to for our [SmartChain UI](https://github.com/code-smartchain/smartchainhApp) to register each new Client using the Frontend
and spins up a new hApp instance for him. He then handles the connections so each client talks with his hApp Instance over a JsonRPC Websocket Connection and gets the results of his call.
This Service only serves as a middleman between the [UI](https://github.com/code-smartchain/smartchainhApp) and the [hApp](https://github.com/code-smartchain/smartchainhApp).

## Authors

* **Martin** - *Initial work* - [SpringHawk](https://github.com/SpringHawk)
* **Timon** - *Initial work* - [8BitJonny](https://github.com/8BitJonny)


See also the list of [contributors](https://github.com/code-smartchain) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

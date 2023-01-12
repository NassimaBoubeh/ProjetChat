
# Application de chat

Une application réalisée en mode non connecté basé sur le protocole **UDP**.
Elle décrit la communication **un à un** ou **un à groupe**.

Pour la faire, j'ai choisi de faire trois packages:
- server

- client

- presentation
#### server:
| Classe       | Description                |
| :--------    | :------------------------- |
| `UDPServer`  | contient les methodes de serveur |
| `serverMain` | contient le main pour lancer le serveur |

| Interface          | Description                |
| :--------          | :------------------------- |
| `Authentification` | implemanter par la classe UDPServer pour inscrire et connecter le client   |

#### client:
| Classe       | Description                |
| :--------    | :------------------------- |
| `Client`  | une petite classe pour avoir les informations sur le client |
| `Session` | permet juste de stocker les clients qui sont connectés |
| `UDPClient` | la classe qui permet au client d'envoyer et recevoir  |

#### presentation:
| Classe       | Description                |
| :--------    | :------------------------- |
| `Fenetre`  | une classe qui contient des méthodes, chaque méthode lance une fenetre associée a cette méthode |


## déploiement

Pour exécuter le programme, il faut d'abords lancer xampp pour ouvrir la base de données.
```bash
  chat.sql
```
Après il faut lancer le main de serveur en premier qu'il existe dans la classe serverMain.

```bash
  run serverMain
```
La classe Fenetre décrit les clients qu'ils vont connécter au serveur.
Vous pouvez lancer plusieurs Fenetre.

```bash
  run Fenetre
```

## Tech Stack

**Base de Données:** JDBC





## Authors

- [NassimaBoubeh](https://www.github.com/nassimaboubeh)


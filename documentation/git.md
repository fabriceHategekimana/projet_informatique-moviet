# Utilisation de git


## Les branches


| action         | commande         |
|----------------|------------------|
| check branches | branch           |
| change branch  | checkout [name]  |
| create branch  | branch [name]    |
| delete branch  | branch -d [name] |

Check branches 
```bash
$ git branch
```

Go to branch [name]
```bash
$ git checkout [name]
```

Create branch [name]
```bash
$ git branch [name]
```

Delete branch [name] (locally)
```bash
$ git branch -d [name]
```

Delete branch [name] (in the repository)
```bash
$ git push origin --delete [name]
```


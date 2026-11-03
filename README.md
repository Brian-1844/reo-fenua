# Reo Fenua

**Messagerie polynésienne qui fonctionne sans internet ni réseau mobile.**

Les téléphones se relaient les messages de proche en proche, en Bluetooth. C'est utile pendant un cyclone, une coupure du câble sous-marin ou dans une vallée sans réseau.

Reo Fenua est une version polynésienne de [bitchat-android](https://github.com/permissionlesstech/bitchat-android) :
- **couleurs du fenua** : bleu océan, turquoise lagon, sable, rouge hibiscus, jaune vanille ;
- **texte plus grand** pour être lisible par tous ;
- **nom en reo** : « Reo Fenua », la voix du pays.

---

## Contenu du dépôt

| Dossier | Rôle |
|---|---|
| `docs/index.html` | Prototype cliquable des écrans (Accueil, Discussion, Secours), à ouvrir dans un navigateur |
| `.github/workflows/build-apk.yml` | **Le seul fichier indispensable** : il construit l'APK Android sur GitHub, avec les couleurs, le nom et le relais automatique déjà inclus |
| `android/` | Copie des fichiers de couleurs, pour référence (non nécessaire au build) |
| `LICENSE` | Licence GPL-3.0 (celle de bitchat-android) |

---

## Construire l'application Android (APK)

### 1. Créer le fichier de build (marche aussi depuis un téléphone)
1. Dans le dépôt : **Add file** → **Create new file**.
2. Comme nom, tapez exactement : `.github/workflows/build-apk.yml`. Les `/` créent les dossiers tout seuls.
3. Collez le contenu du fichier `build-apk.yml`, puis **Commit changes**.

### 2. Lancer la construction
1. Onglet **Actions**. Si GitHub le demande, cliquez sur **« I understand my workflows, go ahead and enable them »**.
2. À gauche, **« Construire l'APK Reo Fenua »**, puis **Run workflow** → **Run workflow**.
3. Attendez 15 à 25 minutes jusqu'à la coche verte ✅.

### 3. Télécharger
- Ouvrez l'onglet **Releases** du dépôt, puis téléchargez **`reo-fenua.apk`**.
- Sur le téléphone, ouvrez l'APK et autorisez « Installer des applications inconnues ».

> L'APK est une version de test (*debug*). Elle porte le même identifiant que BitChat : désinstallez BitChat avant si vous l'avez déjà.

### En cas de croix rouge ❌
Cliquez sur l'exécution, puis sur l'étape en rouge, et copiez les dernières lignes du message d'erreur. Elles indiquent exactement ce qui bloque.

---

## Relais automatique entre les moyens de communication

L'app choisit seule le meilleur chemin disponible, sans réglage :

| Ordre | Moyen | Portée | Quand |
|---|---|---|---|
| 1 | **Bluetooth** | 10–30 m par saut, relayé de téléphone en téléphone | Toujours actif |
| 2 | **Wi-Fi Aware** (Wi-Fi direct entre téléphones) | Plus loin et plus rapide que le Bluetooth (photos, vocaux) | Activé par défaut dans Reo Fenua, sur les téléphones compatibles |
| 3 | **Internet (Nostr)** | Monde entier | Dès qu'une connexion revient (4G, box, satellite) |

Les messages qui ne trouvent aucun chemin sont gardés, puis envoyés dès qu'un chemin apparaît.

---

## Voir le prototype en ligne (GitHub Pages)

1. **Settings** → **Pages**.
2. *Source* : `Deploy from a branch`, branche `main`, dossier `/docs`.
3. Le prototype sera visible à l'adresse `https://<votre-nom>.github.io/reo-fenua/`.

Le prototype montre seulement les écrans. Les envois sont simulés.

---

## Comment fonctionne le réseau

1. **Détection** : chaque téléphone émet en Bluetooth basse énergie et repère les autres à environ 10–30 m.
2. **Relais** : un message chiffré saute de téléphone en téléphone (jusqu'à 7 sauts) jusqu'au destinataire.
3. **Attente** : si personne n'est à portée, le message est gardé, puis transmis dès qu'un téléphone passe.
4. **Retour d'internet** : l'app peut aussi passer par le réseau Nostr quand une connexion existe.
5. **Vie privée** : pas de compte, pas de numéro, pas de serveur. Les messages privés sont chiffrés de bout en bout.

**Limites** : la portée est courte et le débit faible. C'est parfait pour du texte, des photos légères et des messages vocaux courts, pas pour la vidéo. Pour relier des vallées ou des îles, il faut des relais (Wi-Fi longue portée, LoRa / [Meshtastic](https://github.com/meshtastic/Meshtastic-Android)).

---

## Modifier les couleurs

Les couleurs se trouvent dans :
- `android/app/src/main/java/com/bitchat/android/ui/theme/Theme.kt` (thèmes clair et sombre) ;
- `android/app/src/main/java/com/bitchat/android/ui/theme/BitchatPalette.kt` (couleurs d'accent).

Pour changer la taille du texte, modifiez le `17` dans `android/apply.sh`.

---

## Licence

Ce projet est dérivé de bitchat-android, publié sous **GPL-3.0**. Reo Fenua reste donc sous GPL-3.0 : le code source doit rester public et ouvert à tous.

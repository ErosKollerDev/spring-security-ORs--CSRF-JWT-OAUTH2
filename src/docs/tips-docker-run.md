jdbc:mysql://localhost:3306/mysql?allowPublicKeyRetrieval=true&useSSL=false

user: root
pass: root


----------------------------------

## Encoding Base64
openssl base64 -d -in encoded_base64.txt -out decoded_base64.txt
The command line above decodes a base64 encoded file. Here's a breakdown of the command:

- `openssl base64 -d`: This specifies the OpenSSL tool to decode base64 encoded data.
- `-in encoded_base64.txt`: This specifies the input file that contains the base64 encoded data.
- `-out decoded_base64.txt`: This specifies the output file where the decoded plaintext data will be saved.

In summary, this command reads the base64 encoded data from `encoded_base64.txt`, decodes it, and saves the output in `decoded_base64.txt`.
----------------------------------
## Encrypting Symmetric
openssl enc -aes-256-cbc -pass pass:12345 -pbkdf2 -in plain.txt -out encrypt.txt -base64

### The command line above encrypts a file using the AES-256-CBC encryption algorithm.
### Here's a breakdown of the command:

 - `openssl enc -aes-256-cbc`: This specifies the OpenSSL tool to perform encryption/decryption using the AES-256-CBC cipher.
 - `-pass pass:12345`: This specifies the password for encrypting the file as"12345".
 - `-pbkdf2`: This specifies that we want to use the PBKDF2 (password-based key derivation function 2) algorithm to derive the encryption key from the password.
 - `-in plain.txt`: This specifies the input file to be encrypted.
 - `-out encrypt.txt`: This specifies the output file name for the encrypted file.
 - `-base64`: This specifies that the output should be encoded in base64 format.

openssl (encrypt) <using 256>  <password> <using pbkdf2> <load this file> <save this file> <base64>

## Decrypting Symmetric
openssl enc -aes-256-cbc  -base64 -pass pass:12345 -d -pbkdf2 -in encrypted.txt -out decrypt.txt
The command line above decrypts a file that was encrypted using the AES-256-CBC encryption algorithm. Here's a breakdown of the command:

- `openssl enc -aes-256-cbc`: This specifies the OpenSSL tool to perform encryption/decryption using the AES-256-CBC cipher.
- `-base64`: This indicates that the input data is base64 encoded, and the output will also be base64 encoded.
- `-pass pass:12345`: This specifies the password used for encryption/decryption. In this case, it's `12345`.
- `-d`: This flag indicates that the operation is decryption.
- `-pbkdf2`: This specifies the use of Password-Based Key Derivation Function 2, which is a recommended method for deriving encryption keys from passwords.
- `-in encrypted.txt`: This specifies the input file that contains the encrypted data.
- `-out decrypt.txt`: This specifies the output file where the decrypted data will be saved.

In summary, this command decrypts the `encrypted.txt` file using the specified password and saves the decrypted content in `decrypt.txt`.
----------------------------------

## Hashing
echo -n "ErosKoller" | openssl dgst -sha256
The command line above hashes the string "ErosKoller" using the SHA-256 algorithm. Here's a breakdown of the command:

- `echo -n "ErosKoller"`: This prints the string "ErosKoller" to the standard output without a trailing newline. The 
- `-n` flag is used to ensure there is no newline character at the end of the output, which is important for consistent hash results.
- `openssl dgst -sha256`: This specifies the OpenSSL tool to perform the hash operation using the SHA-256 algorithm.
- `-sha256`: This specifies the SHA-256 algorithm to use for the hash operation.

In summary, this command hashes the string "ErosKoller" using the SHA-256 algorithm and prints the resulting hash value to the standard output.

----------------------------------

## Brute force Attack


----------------------------------

## KeyCloak
docker run -p 8180:8080 -d -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.0.5 start-dev

Docs
### Keycloak API's
http://localhost:8180/realms/springbootsecuritydev/.well-known/openid-configuration

Access Token URL
http://localhost:8180/realms/springbootsecuritydev/protocol/openid-connect/token

http://localhost:8180/realms/springbootsecuritydev/protocol/openid-connect/certs

Keycloak 
user: eroskoller
pass: eroskoller

Realm: springbootsecuritydev
clientId: keycloak_clientid
Client Secret: NmHalpSOAHjWBy8UYYcvwhCO7JEP0IP9

----------------------------------
Opaque Toke

clientId: keycloak_clientid_introspect
clientSecret: d3MhUJX4PkcVgfTRL6qEYI1cU30LGpEK

----------------------------------
Grant Type Flow

clientId: client_4_auth_grant_type_flow
clientSecret: lEdiKbesl5iJtSH8ObBq1CvQfE7uY9DT

User
name: zero@gmail.com
pass: zero123456

----------------------------------

PKCE

clientId: client_4_pkce

User
name: zero@gmail.com
pass: zero123456


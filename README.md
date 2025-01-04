# Dokumentasi PBO

## Register User

Endpoint :POST ${base-url}/api/users

Request Body :

```json
{
    "email":"handityaa@gmail.com",
    "username":"handityaa"
    "password":"farhan25",
    "name":"farhan editya"

}
```

Response body (success):

```json
{
  "data": "OK"
}
```

Response body (failed):

```json
{
  "data": null,
  "errors": "Email already registered"
}
```

## Login user

Endpoint : POST ${base-url}/api/auth/login

Request Header :

- X-API-TOKEN : Token (mandatory)

Request body:

```json
{
  "email": "handityaa@gmail.com",
  "password": "farhan25"
}
```

Response body (Success) :

```json
{
    "data": {
        "token": "TOKEN",
        "expiredAt": 1735652476672 // dalam miliseconds,
        "role": "ADMIN"
    },
    "errors": null
}
```

Response body (Failed):

```json
{
  "data": null,
  "errors": "Email or password wrong"
}
```

## Get user

Endpoint : GET ${base-url}/api/users/current

Request Header :

- X-API-TOKEN : Token (mandatory)

Response body (Success):

```json
{
  "data": {
    "email": "gohealthyapp25@gmail.com",
    "name": "Administrator",
    "username": "Admin 1",
    "role": null
  },
  "errors": null
}
```

Response body (failed):

```json
{
  "errors": "Unauthorized"
}
```

##

## Update user

Endpoint : PATCH ${base-url}/api/users/current

Request Header :

- X-API-TOKEN : Token (mandatory)

Request body :

```json
{
  "username": "Han",
  "name": "farhan editya",
  "password": "han25678"
}
```

Response body (Success) :

```json
{
  "data": {
    "email": "gohealthyapp25@gmail.com",
    "name": "Administrator",
    "username": "Admin 1",
    "role": null
  },
  "errors": null
}
```

Response body (failed, 401) :

```json
{
  "errors": "Unauthorized"
}
```

## Logout User

Endpoint : DELETE ${base-url}/api/auth/logout

Request Header :

- X-API-TOKEN : Token (mandatory)

Response Body (Success):

```json
{
  "data": "OK"
}
```

# Consumption API

## Create Consumption

Endpoint : POST ${base-url}/api/food-consumption

Request Header :

- X-API-TOKEN : Token (mandatory)

Request Body:

```json
{
  "foodName": "Nasi goreng",
  "calories": 52.0,
  "consumptionDate": "2024-12-30T10:15:30",
  "quantity": 1
}
```

Response body (Success) :

```json
{
  "data": {
    "foodId": "Random UUID String",
    "foodName": "Nasi goreng",
    "calories": 52.0,
    "consumptionDate": "2024-12-30T10:15:30",
    "quantity": 1
  },
  "errors": null
}
```

## Get Consumption

Endpoint : GET {base-url}/api/food-consumption/${foodId}

Request Header :

- X-API-TOKEN : Token (mandatory)

Response body (Success)

```json
{
  "data": {
    "foodId": "Random UUID String",
    "foodName": "Nasi goreng",
    "calories": 52.0,
    "consumptionDate": "2024-12-30T10:15:30",
    "quantity": 1
  },
  "errors": null
}
```

Response body (Failed) :

```json
{
  "data": null,
  "errors": "Food not found"
}
```

## Update Consumption

Endpoint : PUT {base-url}/api/food-consumption/{foodId}

Request Header :

- X-API-TOKEN : Token (mandatory)

Request body :

```json
{
  "foodName": "Sate Padang",
  "calories": 32.0,
  "consumptionDate": "2024-12-120T10:15:30",
  "quantity": 1
}
```

Response Body (Success):

```json
{
  "data": {
    "foodId": "Random UUID String",
    "foodName": "Sate Padang",
    "calories": 32.0,
    "consumptionDate": "2024-12-120T10:15:30",
    "quantity": 1
  },
  "errors": null
}
```

Response Body (Failed):

```json
{
  "data": null,
  "errors": "Food not found"
}
```

## Get All Food

Endpoint : GET {base-url}/api/users/food-consumption/

Request Header :

- X-API-TOKEN : Token (mandatory)

Response Body (success) :

```json
{
  "data": [
    {
      "foodId": "cfd91559-cbda-4bb7-befe-a13d0967b9a0",
      "foodName": "Nasi goreng",
      "calories": 52.0,
      "consumptionDate": "2024-12-30T10:15:30",
      "quantity": 1
    }
  ],
  "errors": null
}
```

# Delete food By Id

Endpoint : DELETE {base-url}/api/food-consumption/{foodId}

Request Header :

- X-API-TOKEN : Token (mandatory)

Response Body ( Success )

```json
{
  "data": "OK"
}
```

Response Body (Failed):

```json
{
  "data": null,
  "errors": "Food not found"
}
```

# Schedule API

## Create Schedule

Endpoint : POST {base-url}/api/schedule
Request Header :

- X-API-TOKEN : Token (mandatory)

Request Body :

```json
{
    "scheduleName":"Minum obat batuk",
    "scheduleDescription": "Jangan lupa minum obat yak",
    "scheduleTime" : 2024-12-30T10:15:30,
    "scheduleType" : "Minum Obat",
}
```

Response Body (Success) :

```json
{
    "data": {
        "scheduleId" : "Random UUID String"
        "scheduleName": "Minum obat batuk",
        "scheduleDescription": "Jangan lupa minum obat ya",
        "scheduleTime": 2024-12-30T10:15:30 ,
        "secheduleType" : "Minum Obat",
    },
    "errors": null
}
```

## Get schedule By id

base_url = https://be-gohealthy-production.up.railway.app/

Endpoint : GET {base_url}/api/schedule/{scheduleId}
Request Header :

- X-API-TOKEN : Token (mandatory)

Response Body (Success):

```json
{
  "data": {
    "scheduleId": "Random UUID String",
    "scheduleName": "Minum obat batuk",
    "scheduleDescription": "Jangan lupa minum obat batuk",
    "scheduleTime": "2024-12-30T10:15:30",
    "scheduleType": "Minum Obat"
  },
  "errors": null
}
```

Response Body (Failed):

```json
{
  "data": null,
  "errors": "Schedule not found"
}
```

## Update Schedule

base_url = https://be-gohealthy-production.up.railway.app/

Endpoint : PUT {base_url}/api/schedule/{scheduleId}
Request Header :

- X-API-TOKEN : Token (mandatory)

Request Body :

```json
{
  "scheduleName" : "new schedule",
  "scheduleDescription":"new description",
  "scheduleTime" :"2024-12-30T10:15:30"
  "scheduleType" : "new Type schedule",
}
```

Response Body (Success) :

```json
{
  {
  "data": {
     "scheduleId": "Random UUID String",
    "scheduleName": "Minum obat batuk",
    "scheduleDescription": "Jangan lupa minum obat batuk",
    "scheduleTime": "2024-12-30T10:15:30",
    "scheduleType": "Minum Obat"
  },
  "errors": null
}
}
```

Response Body (Failed):

```json
{
  "data": null,
  "errors": "Schedule not found"
}
```

## Delete by Schedule id

base_url = https://be-gohealthy-production.up.railway.app/

Endpoint : DELETE {base_url}/api/schedule/{scheduleId}
Request Header :

- X-API-TOKEN : Token (mandatory)

Response Body (Success) :

```json
{
  "data": "OK",
  "errors": null
}
```

Response Body (Failed):

```json
{
  "data": null,
  "errors": "Schedule not found"
}
```

## Get All Schedule

base_url = https://be-gohealthy-production.up.railway.app/
Endpoint : GET {base_url}/api/users/schedule
Request Header :

- X-API-TOKEN : Token (mandatory)

Response Body (Success)

```json
{
  "data": [
    {
      "scheduleId": "Random UUID String",
      "scheduleName": "Minum obat batuk",
      "scheduleDescription": "Jangan lupa minum obat batuk",
      "scheduleTime": "2024-12-30T10:15:30",
      "scheduleType": "Minum Obat"
    }
  ],
  "errors": null
}
```

# Content API

## Create Content

base_url = https://be-gohealthy-production.up.railway.app/

Endpoint POST {base_url}/api/content

Request Role = "ADMIN"
Request Header :

- X-API-TOKEN : Token (mandatory)

Request Body :

```json
{
  "contentTitle": "5 Rekomendasi Kegiatan Sehat di Pagi Hari",
  "bodyContent": "Memulai pagi dengan kegiatan yang sehat dapat memberikan energi positif dan meningkatkan produktivitas sepanjang hari. Berikut ini adalah 5 rekomendasi kegiatan sehat yang bisa Anda lakukan di pagi hari:\n\n1. **Minum Segelas Air Hangat**\nSetelah bangun tidur, tubuh membutuhkan rehidrasi. Minum segelas air hangat dapat membantu membersihkan sistem pencernaan, meningkatkan metabolisme, dan memberikan dorongan energi.\n\n2. **Olahraga Ringan atau Peregangan**\nMelakukan olahraga ringan seperti jalan kaki, yoga, atau peregangan dapat membantu melancarkan aliran darah, mengurangi stres, dan meningkatkan fleksibilitas tubuh. Tidak perlu lama—cukup 15-30 menit sudah memberikan manfaat yang besar.\n\n3. **Sarapan Bergizi**\nPastikan Anda mengonsumsi sarapan yang mengandung karbohidrat kompleks, protein, lemak sehat, dan serat. Misalnya, roti gandum dengan telur rebus, smoothie buah, atau oatmeal dengan potongan buah segar.\n\n4. **Meditasi atau Berdoa**\nMenghabiskan beberapa menit untuk meditasi atau berdoa dapat membantu menenangkan pikiran, meningkatkan konsentrasi, dan memulai hari dengan suasana hati yang positif.\n\n5. **Menulis Agenda atau Rencana Harian**\nLuangkan waktu untuk menulis daftar tugas atau rencana kegiatan yang ingin Anda selesaikan hari itu. Hal ini membantu Anda lebih fokus dan terorganisir dalam menjalani aktivitas.\n\nCobalah terapkan beberapa atau semua kegiatan di atas untuk menciptakan pagi yang lebih produktif dan sehat. Dengan memulai hari secara positif, Anda dapat mencapai lebih banyak hal dengan suasana hati yang lebih baik!"
}
```

Response Body (Success)

```json
  {
    "data": {
        "contentId" : "Random UUID String"
        "contentTitle": "5 Rekomendasi Kegiatan Sehat di Pagi Hari",
        "bodyContent": "Memulai pagi dengan kegiatan yang sehat dapat memberikan energi positif dan meningkatkan produktivitas sepanjang hari. Berikut ini adalah 5 rekomendasi kegiatan sehat yang bisa Anda lakukan di pagi hari:\n\n1. **Minum Segelas Air Hangat**\nSetelah bangun tidur, tubuh membutuhkan rehidrasi. Minum segelas air hangat dapat membantu membersihkan sistem pencernaan, meningkatkan metabolisme, dan memberikan dorongan energi.\n\n2. **Olahraga Ringan atau Peregangan**\nMelakukan olahraga ringan seperti jalan kaki, yoga, atau peregangan dapat membantu melancarkan aliran darah, mengurangi stres, dan meningkatkan fleksibilitas tubuh. Tidak perlu lama—cukup 15-30 menit sudah memberikan manfaat yang besar.\n\n3. **Sarapan Bergizi**\nPastikan Anda mengonsumsi sarapan yang mengandung karbohidrat kompleks, protein, lemak sehat, dan serat. Misalnya, roti gandum dengan telur rebus, smoothie buah, atau oatmeal dengan potongan buah segar.\n\n4. **Meditasi atau Berdoa**\nMenghabiskan beberapa menit untuk meditasi atau berdoa dapat membantu menenangkan pikiran, meningkatkan konsentrasi, dan memulai hari dengan suasana hati yang positif.\n\n5. **Menulis Agenda atau Rencana Harian**\nLuangkan waktu untuk menulis daftar tugas atau rencana kegiatan yang ingin Anda selesaikan hari itu. Hal ini membantu Anda lebih fokus dan terorganisir dalam menjalani aktivitas.\n\nCobalah terapkan beberapa atau semua kegiatan di atas untuk menciptakan pagi yang lebih produktif dan sehat. Dengan memulai hari secara positif, Anda dapat mencapai lebih banyak hal dengan suasana hati yang lebih baik!",
        "created_at": 2024-12-30T10:15:30
    },
    "errors": null
}
```

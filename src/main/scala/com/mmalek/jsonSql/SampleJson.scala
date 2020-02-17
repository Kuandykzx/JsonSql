package com.mmalek.jsonSql

object SampleJson {
  val list =
    """
      {
          "items":[
              {
                  "id":1,
                  "address":{
                      "street": "Obrońców Wybrzeża",
                      "city": "Gdańsk"
                  }
              },
              {
                  "id":2,
                  "address": null
              },
              {
                  "id":3,
                  "age":30,
                  "name":"Robert",
                  "surname":"Cobb",
                  "fullname":"Jack Gibson",
                  "isEmployee":true,
                  "address":{
                      "street": "Obrońców Wybrzeża",
                      "city": "Gdańsk"
                  }
              },
              {
                  "id":4,
                  "age":5,
                  "name":"Sue",
                  "surname":"Burgess",
                  "fullname":"Jean Grant",
                  "isEmployee":true,
                  "address":{
                      "street": "Irydowa",
                      "city": "Gdynia"
                  }
              },
              {
                  "id":5,
                  "age":16,
                  "name":"Timothy",
                  "surname":"Burnett",
                  "fullname":"Debra McKenna",
                  "isEmployee":false
              },
              {
                  "id":6,
                  "age":4,
                  "name":"Stanley",
                  "surname":"Rich",
                  "fullname":"Annie Finch",
                  "isEmployee":true,
                  "address": null
              },
              {
                  "id":7,
                  "age":15,
                  "name":"Molly",
                  "surname":"Murphy",
                  "fullname":"Karen Grimes",
                  "isEmployee":true,
                  "address": {
                      "street": "Obrońców Wybrzeża",
                      "city": "Gdańsk"
                  }
              },
              {
                  "id":8,
                  "age":24,
                  "name":"Melanie",
                  "surname":"Graves",
                  "fullname":"Jose Wolf",
                  "isEmployee":true,
                  "address":{
                      "street": "Irydowa",
                      "city": "Gdynia"
                  }
              },
              {
                  "id":9,
                  "age":2,
                  "name":"James",
                  "surname":"Willis",
                  "fullname":"Hannah Teague",
                  "isEmployee":true,
                  "address":{
                      "street": "Irydowa",
                      "city": "Gdynia"
                  }
              },
              {
                  "id":10,
                  "age":29,
                  "name":"Warren",
                  "surname":"Lawrence",
                  "fullname":"Leo Locklear",
                  "isEmployee":false,
                  "address":{
                      "street": "Irydowa",
                      "city": "Gdynia"
                  }
              }
          ]
      }
      """.stripMargin
  val single =
    """
      {
          "id":1,
          "age":1,
          "name":"Ralph",
          "surname":"Garcia",
          "fullname":"Raymond Mann",
          "isEmployee":true,
          "address":{
              "street": "Obrońców Wybrzeża",
              "city": "Gdańsk"
          }
      }
      """.stripMargin
}

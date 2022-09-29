package com.example.scheduling.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Foo {
    @Id
    var id:Long? = null

    var status:String = "created"
}
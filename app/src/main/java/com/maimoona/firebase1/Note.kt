package com.maimoona.firebase1

class Note {
    var id: String?=null
    var title: String?=null
    var details: String? =null
    var date: String?=null

    constructor(){

    }

    constructor(id: String?, title:String, details:String, date:String){
        this.id=id
        this.title=title
        this.details=details
        this.date=date
    }
}
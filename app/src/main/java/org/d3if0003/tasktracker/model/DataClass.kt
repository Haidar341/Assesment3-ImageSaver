package org.d3if0003.tasktracker.model

class DataClass {
    var id: String? = null
    var dataTitle: String? = null
    var dataDesc: String? = null
    var dataPurpose: String? = null // Change from dataPriority to dataPurpose
    var dataImage: String? = null

    constructor(id: String?, dataTitle: String?, dataDesc: String?, dataPurpose: String?, dataImage: String?) {
        this.id = id
        this.dataTitle = dataTitle
        this.dataDesc = dataDesc
        this.dataPurpose = dataPurpose
        this.dataImage = dataImage
    }

    constructor()
    {}
}

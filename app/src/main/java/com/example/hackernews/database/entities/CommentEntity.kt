package com.example.hackernews.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// I decided to separate comments and replies into two tables but idk how smart it is.
// both tables will contain parent column, which is pointer to parent inside tree structure,
// so when I use this approach i know comment table will hold only parent references to Post, so I can implement one to many relationship with Post table
// and when I save replies, I know that parent column is another Comment so I can also achieve this one to many relationship with Comment table
// but the main problem of this approach it would duplicate all the data inside these two tables
@Entity
data class CommentEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "comment_id") // tbl_name prefix increases readability when using joins over multiple tables
    var id: Int,
    var by: String,
    @ColumnInfo(name = "parent_id") // for now we will use just parent_id, if we decide to separate two tables this would be "post_id"
    var parent: Int,
    var text: String,
    var time: Long,
    var type: String
) : Serializable

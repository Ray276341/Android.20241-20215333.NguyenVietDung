

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gmailview.R

data class EmailItem(
    val senderName: String,
    val sendDate: String,
    val emailTitle: String,
    val emailContent: String,
    var isRead: Boolean,
    var isStarred: Boolean
)

class EmailAdapter(private val emailList: List<EmailItem>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    inner class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderName: TextView = itemView.findViewById(R.id.sender_name)
        val sendDate: TextView = itemView.findViewById(R.id.send_date)
        val emailTitle: TextView = itemView.findViewById(R.id.email_title)
        val emailContent: TextView = itemView.findViewById(R.id.email_content)
        val senderAvatar: ImageView = itemView.findViewById(R.id.sender_avatar)
        val starIcon: ImageView = itemView.findViewById(R.id.star_icon)

        fun bind(email: EmailItem) {
            senderName.text = email.senderName
            sendDate.text = email.sendDate
            emailTitle.text = email.emailTitle
            emailContent.text = email.emailContent

            // Set text styles based on read/unread status
            val textStyle = if (email.isRead) {
                android.graphics.Typeface.NORMAL
            } else {
                android.graphics.Typeface.BOLD
            }

            senderName.setTypeface(null, textStyle)
            emailTitle.setTypeface(null, textStyle)

            // Update star icon based on starred status
            starIcon.setImageResource(if (email.isStarred) R.drawable.ic_star_filled else R.drawable.ic_star_empty)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emailList[position])
    }

    override fun getItemCount(): Int {
        return emailList.size
    }
}

<template>
    <div>

        <h2>
            Title: {{ news.title }}
        </h2>

        <h6 style="margin-top: 18px">
            Category: {{ news.categoryId}}
        </h6>

        <div v-if="tags">
            <h6 style="margin-top: 12px">Tags:</h6>
            <h6 v-for="tagName in news.tags" :key = "tagName" @click="getNewsByTag(tagName)">
                {{tagName}}
            </h6>
        </div>
        <h5  style="margin-top: 12px">
            Author: {{ news.author }}
        </h5>

        <h6  style="margin-top: 12px">
            Created: {{ (new Date(news.createdAt)).toLocaleDateString("en-us", { weekday: "long", year: "numeric", month: "short", day: "numeric",}) }}
        </h6>
        <br>
        <h4>Content:</h4>
        <p>
            {{ news.content }} <br><br>
        </p>
        <br>
        <h2>Add comment:</h2>
        <div class="form-group">
            Date: {{currentDateTime()}}
        </div>
        <br>
        <form method="post" v-on:submit.prevent = "postComment()" >
            <div class="form-group">
                <label for="name">Name</label>
                <input required v-model="commentAuthor" type="text" class="form-control" id="name" placeholder="Enter your name">
            </div>
            <br>

            <div class="form-group">
                <label for="content">Comment</label>
                <input required v-model="commentContent" type="text" class="form-control" id="content" placeholder="Comment">
            </div>
            <br>
            <button type="submit" class="btn btn-primary mt-2">Publish</button>
        </form>

        <br> <br>
        <h3 style="margin-top: 12px">Comments:</h3>
        <h6 v-for="comment in news.comments" :key = "comment.id">

            Author:
            {{comment.author}}

            <br>
            {{(new Date(comment.createdAt)).toLocaleDateString("en-us", { weekday: "long", year: "numeric", month: "short", day: "numeric",})}}
            <br>
            <b-card style="margin-top: 10px">
                <h4 style="margin-top: 15px;margin-left: 20px">{{comment.content}}</h4>
            </b-card>
        </h6>
    </div>
</template>


<script>
export default {
    name: "NewsDetails",

    data() {
        return {
            news: {},
            tags: [],
            comments: [],
            commentAuthor: null,
            commentContent: null,
        }
    },
    mounted() {
        this.$axios.put(`/api/news/visited/${this.$route.params.id}`).then((response) => {
            this.news = response.data
            this.news.tags = response.data.tags.split(",").map(function(item) { return item.trim(); });
        });
    },
    created() {

    },
    methods: {
        postComment(){
            this.$axios.post(`/api/comments`, {
                "newsId": this.news.id,
                "author": this.commentAuthor,
                "content": this.commentContent,
                "createdAt": Date.now()
            }).then(() => {
                window.location.reload();
            });
        },
        currentDateTime() {
            const current = new Date();
            const date = current.getFullYear()+'-'+(current.getMonth()+1)+'-'+current.getDate();
            const time = current.getHours() + ":" + current.getMinutes() + ":" + current.getSeconds();
            const dateTime = date +' '+ time;

            return dateTime;
        },
        getNewsByTag(tagName) {
            this.$router.push(`/news/by-tag/${tagName}`);
        }
    }
}
</script>

<style scoped>

</style>
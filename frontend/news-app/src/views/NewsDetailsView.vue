<template>
    <div>
        <NewsDetails :news="news"></NewsDetails>
    </div>
</template>

<script>
import NewsDetails from "../components/NewsDetails";
export default {
    name: "NewsDetailsView",

    components: {NewsDetails},
    data() {
        return {
            news: {},
        }
    },
    created() {
        this.$axios.get(`news/${this.$route.params.id}`).then((response) => {
            this.news = response.data;
        });
    }
}
</script>

<style scoped>

</style>
import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/news',
    name: 'News',
    meta: {
      authRequired: false,
    },
    component: () => import(/* webpackChunkName: "about" */ '../views/News.vue')
  },
  {
    path: '/news/:id',
    name: 'NewsDetailsView',
    meta: {
      authRequired: false,
    },
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/NewsDetailsView.vue')
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

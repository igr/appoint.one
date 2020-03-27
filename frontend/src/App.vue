<template>
  <div id="app">
    <HelloWorld msg="Welcome to App"/>
    <NumberOfTimeslots/>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import HelloWorld from './components/HelloWorld.vue';
import NumberOfTimeslots from './components/NumberOfTimeslots.vue';

@Component({
  components: {
    HelloWorld,
    NumberOfTimeslots,
  },
})
export default class App extends Vue {}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

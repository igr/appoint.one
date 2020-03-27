<template>
  <div id="app">
    <Error v-show="statusMessage !== ''" :errorMessage="statusMessage" />
    <HelloWorld msg="Welcome to App"/>
    <NumberOfTimeslots v-on:add-error="addError"/>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import HelloWorld from './components/HelloWorld.vue';
import NumberOfTimeslots from './components/NumberOfTimeslots.vue';
import Error from './components/Error.vue';

@Component({
  components: {
    HelloWorld,
    NumberOfTimeslots,
    Error,
  },
  data() {
    return {
      statusMessage: '',
    };
  },
  methods: {
    addError(statusText) {
      this.statusMessage = statusText;
    },
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

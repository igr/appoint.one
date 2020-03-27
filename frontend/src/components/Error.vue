<template>
    <div class="error">
        <h1> Error: &nbsp; {{ errorMessage }}</h1>
        <br>
        <hr>
    </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

    @Component
export default class Error extends Vue {
        @Prop() private errorMessage!: string;
}
</script>

<style scoped>
    .error {
        color: red;
    }
</style>

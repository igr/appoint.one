<template>
  <v-row
    id="doc"
    justify="center"
  >
    <v-col
      cols="12"
      md="8"
    >
      <vue-markdown :source="data" />
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import DocApi from '@/api/DocApi';
import VueMarkdown from 'vue-markdown';


@Component({
  name: 'Doctor',
  components: {
    VueMarkdown,
  },
})
export default class extends Vue {
  @Prop({ default: '' })
  readonly id!: number;

  private data = ''

  async created() {
    const content = await DocApi.get(this.id);
    this.data = content.data.content;
  }
}
</script>

<style scoped>
#doc {
  font-size: 1.3em;
}
</style>

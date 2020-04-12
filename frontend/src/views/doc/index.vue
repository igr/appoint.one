<template>
  <v-row
    justify="center"
  >
    <v-col
      cols="12"
      md="8"
    >
      <vue-markdown
        id="article"
        :source="data"
      />
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

<style scoped lang="scss">
#article {
  font-size: 1.3em;
  ul {
    margin-bottom: 1em;
  }
}
</style>

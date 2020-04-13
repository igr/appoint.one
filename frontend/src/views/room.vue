<template>
  <div class="container fill-height align-center">
    <vue-webrtc
      v-show="isConnected"
      ref="webrtc"
      class="fill-height"
      width="100%"
      :room-id="roomId"
      :enable-video="enableVideo"
      @init="onInit"
      @error="onError"
      @joined-room="logEvent"
      @left-room="logEvent"
      @open-room="logEvent"
      @share-started="logEvent"
      @share-stopped="logEvent"
    />
    <div class="row align-center justify-center">
      <v-btn
        v-if="!isConnected"
        rounded
        x-large
        color="success"
        dark
        :loading="isLoading"
        @click="onJoin"
      >
        Započni razgovor
      </v-btn>
    </div>
    <v-btn
      v-if="isConnected"
      large
      color="error"
      absolute
      top
      right
      rounded
      @click="onLeave"
    >
      Prekini razgovor
    </v-btn>
    <v-speed-dial
      v-if="isConnected"
      v-model="fab"
      absolute
      bottom
      left
    >
      <template v-slot:activator>
        <v-btn
          v-model="fab"
          color="blue darken-2"
          dark
          fab
        >
          <v-icon v-if="fab">
            mdi-close
          </v-icon>
          <v-icon v-else>
            mdi-cog
          </v-icon>
        </v-btn>
      </template>
      <v-btn
        fab
        dark
        small
        color="red"
        @click="onLeave"
      >
        <v-icon>mdi-link-off</v-icon>
      </v-btn>
      <v-btn
        fab
        dark
        small
        color="indigo"
        @click="toggleFullScreen"
      >
        <v-icon>mdi-fullscreen</v-icon>
      </v-btn>
    </v-speed-dial>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import { AppModule } from '@/store';
import VueWebrtc from '@/components/WebRTC.vue';

@Component({
  name: 'room',
  components: {
    VueWebrtc,
  },
})
export default class extends Vue {
  @Prop({ default: -1 })
  readonly id!: number;

  $refs!: {
    webrtc: Vue & {
      capture: () => any;
      join: () => void;
      leave: () => void;
      toggleVideo: () => void;
      shareScreen: () => any;
    };
  }

  roomId = '';

  isConnected = false;

  isLoading = false;

  fab = false;

  enableVideo = true;

  mounted() {
    this.roomId = btoa(`podrskapsihoterapeuta.com-${this.id}`);
  }

  onJoin() {
    this.$refs.webrtc.join();
    this.isLoading = true;
  }

  onLeave() {
    this.$refs.webrtc.leave();
    this.isConnected = false;
    this.isLoading = false;
  }

  onInit() {
    this.isConnected = true;
    this.isLoading = false;
  }

  onError(error: any) {
    AppModule.setAlertMessage(error);
    this.isLoading = false;
  }

  toggleVideo() {
    this.enableVideo = !this.enableVideo;
  }

  toggleFullScreen() {
    if (!document.fullscreenElement) {
      document.documentElement.requestFullscreen();
    } else if (document.exitFullscreen) {
      document.exitFullscreen();
    }
  }

  logEvent(event: string) {
    console.log('Event : ', event);
  }
}
</script>

<style lang="scss">
</style>

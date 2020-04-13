<template>
  <div class="video-list">
    <video
      v-for="item in videoList"
      :id="item.id"
      :key="item.id"
      ref="videos"
      autoplay
      playsinline
      :height="cameraHeight"
      :muted="item.muted"
    />
  </div>
</template>

<script lang="ts">
import RTCMultiConnection from 'rtcmulticonnection';

import {
  Component, Vue, Prop,
} from 'vue-property-decorator';

export interface Video {
  id?: string;
  muted?: boolean;
}

export interface Stream {
  streamid?: string;
  type?: 'local' | 'remote';
  stream?: any;
  tracks?: any;
  addEventListener?: HTMLElement['addEventListener'];
  stop?: () => void;
}

@Component({
  name: 'VueWebrtc',
})
export default class extends Vue {
  @Prop({ default: 'private-room' })
  readonly roomId!: string;

  @Prop({ default: 'https://rtcmulticonnection.herokuapp.com:443/' })
  readonly socketURL!: string;

  @Prop({ default: 160 })
  readonly cameraHeight!: number;

  @Prop({ default: true })
  readonly autoplay!: boolean;

  @Prop({ default: 'image/jpeg' })
  readonly screenshotFormat!: string;

  @Prop({ default: true })
  readonly enableAudio!: boolean;

  @Prop({ default: true })
  readonly enableVideo!: boolean;

  @Prop({ default: false })
  readonly enableLogs!: boolean;

  $refs!: {
    videos: HTMLVideoElement[];
  }

  rtcmConnection: any = null;

  localVideo: Video = {};

  videoList: Video[] = [];

  canvas = document.createElement('canvas');

  ctx = this.canvas.getContext('2d');

  mounted() {
    this.rtcmConnection = new RTCMultiConnection();
    this.rtcmConnection.socketURL = this.socketURL;
    this.rtcmConnection.autoCreateMediaElement = false;
    this.rtcmConnection.maxParticipantsAllowed = 2;
    this.rtcmConnection.enableLogs = this.enableLogs;
    this.rtcmConnection.session = {
      audio: this.enableAudio,
      video: this.enableVideo,
    };
    this.rtcmConnection.sdpConstraints.mandatory = {
      OfferToReceiveAudio: this.enableAudio,
      OfferToReceiveVideo: this.enableVideo,
    };
    this.rtcmConnection.onstream = (stream: Stream) => {
      const found = this.videoList.find((video) => video.id === stream.streamid);
      if (found === undefined) {
        const video = {
          id: stream.streamid,
          muted: stream.type === 'local',
        };

        this.videoList.push(video);

        if (stream.type === 'local') {
          this.localVideo = video;
        }
      }

      setTimeout(() => {
        // eslint-disable-next-line no-plusplus
        for (let i = 0, len = this.$refs.videos.length; i < len; i++) {
          if (this.$refs.videos[i].id === stream.streamid) {
            this.$refs.videos[i].srcObject = stream.stream;
            break;
          }
        }
      }, 1000);

      this.$emit('joined-room', stream.streamid);
    };
    this.rtcmConnection.onstreamended = (stream: Stream) => {
      const newList: Video[] = [];
      this.videoList.forEach((item) => {
        if (item.id !== stream.streamid) {
          newList.push(item);
        }
      });
      this.videoList = newList;
      this.$emit('left-room', stream.streamid);
    };
  }

  join() {
    this.rtcmConnection.openOrJoin(this.roomId, (isRoomExist: boolean, roomid: string) => {
      if (isRoomExist === false && this.rtcmConnection.isInitiator === true) {
        this.$emit('opened-room', roomid);
      }
      this.$emit('init');
    });
  }

  leave() {
    this.rtcmConnection.attachStreams.forEach((localStream: Stream) => {
      if (localStream && localStream.stop) localStream.stop();
    });
    this.videoList = [];
  }

  capture() {
    return this.getCanvas().toDataURL(this.screenshotFormat);
  }

  shareScreen() {
    const nav: any = navigator;
    if (nav.getDisplayMedia || nav.mediaDevices.getDisplayMedia) {
      if (nav.mediaDevices.getDisplayMedia) {
        nav.mediaDevices.getDisplayMedia({ video: true, audio: false }).then((stream: Stream) => {
          this.onGettingSteam(stream);
        }, this.getDisplayMediaError).catch(this.getDisplayMediaError);
      } else if (nav.getDisplayMedia) {
        nav.getDisplayMedia({ video: true }).then((stream: Stream) => {
          this.onGettingSteam(stream);
        }, this.getDisplayMediaError).catch(this.getDisplayMediaError);
      }
    }
  }

  private getCanvas() {
    const video = this.getCurrentVideo();
    if (video !== null && !this.ctx) {
      this.canvas.height = video.clientHeight;
      this.canvas.width = video.clientWidth;
      this.ctx = this.canvas.getContext('2d');
      if (this.ctx) this.ctx.drawImage(video, 0, 0, this.canvas.width, this.canvas.height);
    }
    return this.canvas;
  }

  private getCurrentVideo() {
    if (this.localVideo === null) {
      return null;
    }
    // eslint-disable-next-line no-plusplus
    for (let i = 0, len = this.$refs.videos.length; i < len; i++) {
      if (this.$refs.videos[i].id === this.localVideo.id) return this.$refs.videos[i];
    }
    return null;
  }

  private addStreamStopListener(stream: Stream, callback: any) {
    let streamEndedEvent = 'ended';
    if ('oninactive' in stream) {
      streamEndedEvent = 'inactive';
    }
    if (stream && stream.addEventListener) {
      stream.addEventListener(streamEndedEvent, () => callback, false);
    }
  }

  private onGettingSteam(stream: Stream) {
    this.rtcmConnection.addStream(stream);
    this.$emit('share-started', stream.streamid);

    this.addStreamStopListener(stream, () => {
      this.rtcmConnection.removeStream(stream.streamid);
      this.$emit('share-stopped', stream.streamid);
    });
  }

  // eslint-disable-next-line no-unused-vars,@typescript-eslint/no-unused-vars
  private getDisplayMediaError(error: any) {
    // console.log(`Media error: ${JSON.stringify(error)}`);
  }
}

</script>
<style scoped lang="scss">
.video-list {
  background: #000;
  height: auto;
}

.video-list {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  video {
    width: 100%;
    height: 100%;
    object-fit: contain;
    background: #000;
    &:first-child {
      width: auto;
      height: auto;
      max-width: 25%;
      max-height: 25%;
      position: absolute;
      right: 10px;
      bottom: 10px;
      z-index: 1;
    }
  }
}

</style>

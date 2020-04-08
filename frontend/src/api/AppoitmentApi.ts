import http from '@/utils/http';

class AppoitmentApi {
  get = (timeslotId: number) => http({
    url: `/appointments/${timeslotId}`,
    method: 'get',
  });

  downloadIcal = (timeslotId: number) => http({
    url: `/appointments/${timeslotId}/ical`,
    method: 'GET',
    responseType: 'blob',
  }).then((response) => {
    const fileURL = window.URL.createObjectURL(new Blob([response.data]));
    const fileLink = document.createElement('a');
    fileLink.href = fileURL;
    fileLink.setAttribute('download', `${timeslotId}.ics`);
    document.body.appendChild(fileLink);
    fileLink.click();
  });
}

export default new AppoitmentApi();

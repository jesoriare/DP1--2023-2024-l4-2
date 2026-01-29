import tokenService from "./services/token.service";

async function get (route) {
  const token = tokenService.getLocalAccessToken();
  return await fetch(route, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    }})
    .then(response => response.json())
    .catch(error => {
      console.error('Error fetching data:', error);
    });
}

async function create (route,postData) {
  const token = tokenService.getLocalAccessToken();
  return await fetch(route, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(postData),
    })
      .then(response => response.json())
      .catch(error => {
        console.error('Error posting data:', error);
      });
}

async function update (route, putData) {
  const token = tokenService.getLocalAccessToken();
   return await fetch(route, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(putData),
      })
        .then(response => response.json())
        .catch(error => {
          console.error('Error updating data:', error);
        });
}

async function remove (route) {
  const token = tokenService.getLocalAccessToken();
    return await fetch(route, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      })
        .then(response => response.json())
        .catch(error => {
          console.error('Error deleting data:', error);
        });
}

export { create, get, remove, update }

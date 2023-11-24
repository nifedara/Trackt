/**
 * Fetch data from given url
 * @param {*} url
 * @param {*} options
 */
import { rootUrl } from "./baseUrl";

const fetchJSON = async (url, data = {}, type, token = "") => {
  let appUrl = rootUrl + url;
  let body = JSON.stringify(data);
  let headerContent = {
    "Content-type": "application/json",
  };

  if (token !== "") {
    headerContent = {
      "Content-type": "application/json",
      Authorization: `Bearer ${token}`,
    };
  }

  if (type === "PUT" || type === "POST" || type === "PATCH") {
    const res = await fetch(url, {
      method: type,
      headers: headerContent,
      body: body,
    });
    const res2 = await res.json();
    return res2;
  } else {
    const res = await fetch(url, {
      method: type,
      headers: headerContent,
    });
    const res2 = await res.json();
    return res2;
  }
};

export { fetchJSON };

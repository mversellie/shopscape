import {ResolveFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {ShopService} from '../services/shop.service';

export const entityPageResolver: ResolveFn<any> = (route, state) => {
  const entityId = route.paramMap.get("id")
  const routerIn = inject(Router)

  if(entityId != null){
    return inject(ShopService).getShopById(entityId).then(d => d)
      .catch((error) => {
        console.log(error)
        routerIn.navigateByUrl("/notfound")
        //Not reachable but typescript will complain
        return genUselessEntityResponse();
      });
  }
  routerIn.navigateByUrl("/notfound")
  return genUselessEntityResponse();
};

function genUselessEntityResponse(){
  return null;
}

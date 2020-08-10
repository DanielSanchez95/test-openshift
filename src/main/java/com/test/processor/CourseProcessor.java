/**
 * 
 */
package com.test.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.test.dto.RequestCourse;


/**
 * @author Assert Solutions S.A.S.
 *
 */
@Component
public class CourseProcessor implements Processor {

	private List<RequestCourse> listUser = new ArrayList<RequestCourse>();

	
	@Override
	public void process(Exchange exchange) throws Exception {
		List<RequestCourse> list = new ArrayList<RequestCourse>();
		UUID uuid = UUID.randomUUID();

		
		
		switch (String.valueOf(exchange.getProperty("serviceRest"))) {
		case "create":
			list = listUser;
			RequestCourse req = (RequestCourse) exchange.getIn().getBody();
			req.setId(uuid.toString());
			list.add(req);
			listUser = list;
			list = new ArrayList<RequestCourse>();
			break;
		case "update":
			String documentUP = String.valueOf(exchange.getIn().getHeader("document"));

			List<RequestCourse> listUpdate = new ArrayList<>(listUser);
			int iu = 0;
			for (RequestCourse request : listUser) {
				if (request.getId().equals(documentUP)) {
					request = (RequestCourse) exchange.getIn().getBody();
					request.setId(documentUP);
					listUpdate.set(iu, request);
				}
				iu++;
			}
			list = listUpdate;
			listUser = list;
			list = new ArrayList<RequestCourse>();
			break;
		case "delete":
			String documentDE = String.valueOf(exchange.getIn().getHeader("document"));
			List<RequestCourse> listDelete = new ArrayList<>(listUser);
			int i = 0;
			for (RequestCourse request : listUser) {
				if (request.getId().equals(documentDE)) {
					listDelete.remove(i);
				}
				i++;
			}
			list = listDelete;
			listUser = list;
			list = new ArrayList<RequestCourse>();
			break;
		case "list":
			String documentLI = String.valueOf(exchange.getIn().getHeader("document"));
			for (RequestCourse request : listUser) {
				if (request.getId().equals(documentLI)) {
					list.add(request);
					break;
				}
			}
			break;
		case "listall":
			list = listUser;
			break;
		default:
			list = listUser;
			break;
		}
		exchange.getIn().setBody(list);
		exchange.getOut().setBody(list);

	}

}

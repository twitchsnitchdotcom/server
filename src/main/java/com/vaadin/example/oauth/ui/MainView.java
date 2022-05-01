
package com.vaadin.example.oauth.ui;

import com.flowingcode.vaadin.addons.orgchart.OrgChart;
import com.flowingcode.vaadin.addons.orgchart.OrgChartItem;
import com.flowingcode.vaadin.addons.orgchart.extra.TemplateLiteralRewriter;
import com.github.twitch4j.helix.domain.UserList;
import com.vaadin.componentfactory.timeline.Timeline;
import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.example.oauth.data.UserSession;
import com.vaadin.example.service.TwitchService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.annotation.security.PermitAll;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Application main class that is hidden to user before authentication.
 *
 * The class is annotated with {@code @PermitAll} to allow only authenticated
 * users to view the class.
 */
@Route("")
@PermitAll
public class MainView extends VerticalLayout {



    public MainView(UserSession userSession, TwitchService twitchService) {
        UserList execute = twitchService.getTwitchClient().getHelix().getUsers(userSession.getUser().getToken(), null, Collections.singletonList(userSession.getUser().getUsername())).execute();
        Div div = new Div();
        div.setText("Hello " + userSession.getUser().getUsername() + " " + execute.getUsers().get(0).getEmail());
        div.getElement().getStyle().set("font-size", "xx-large");
        setAlignItems(Alignment.CENTER);
        add(div, timeline(), orgChart());
    }

    private Timeline timeline(){
        // create items
        Item item1 = new Item(LocalDateTime.of(2021, 8, 11, 2, 30, 00),
                LocalDateTime.of(2021, 8, 11, 8, 00, 00), "Item 1");
        item1.setId("1");

        Item item2 = new Item(LocalDateTime.of(2021, 8, 11, 9, 00, 00),
                LocalDateTime.of(2021, 8, 11, 17, 00, 00), "Item 2");
        item2.setId("2");

        Item item3 = new Item(LocalDateTime.of(2021, 8, 12, 0, 30, 00),
                LocalDateTime.of(2021, 8, 12, 3, 00, 00), "Item 3");
        item3.setId("3");

        Item item4 = new Item(LocalDateTime.of(2021, 8, 12, 4, 30, 00),
                LocalDateTime.of(2021, 8, 12, 20, 00, 00), "Item 4");
        item4.setId("4");

        Item item5 = new Item(LocalDateTime.of(2021, 8, 12, 21, 30, 00),
                LocalDateTime.of(2021, 8, 13, 01, 15, 00), "Item 5");
        item5.setId("5");

        List<Item> items = Arrays.asList(item1, item2, item3, item4, item5);

        // make them editable
        items.forEach(i -> {
            i.setEditable(true);
            i.setUpdateTime(true);
        });

        // create timeline
        Timeline timeline = new Timeline(items);

        // set timeline range
        timeline.setTimelineRange(
                LocalDateTime.of(2021, 8, 10, 00, 00, 00), LocalDateTime.of(2021, 8, 15, 00, 00, 00));

        // set multiselection to timeline
        timeline.setMultiselect(true);

        return timeline;
    }

    private OrgChart orgChart(){
        OrgChart component1 = getExample1();
        String nodeTemplate =
                "<div class='title'>${item.title}</div>"
                        + "<div class='middle content'>${item.name}</div>"
                        + "${item.data.mail?`<div class='custom content'>${item.data.mail}</div>`:''}";
        component1.setNodeTemplate("item", TemplateLiteralRewriter.rewriteFunction(nodeTemplate));
        component1.addClassName("chart-container");

        component1.setChartTitle(
                "My Organization Chart Demo - Example 1 - CHART EXPORT AS PICTURE AND DRAG & DROP, CUSTOM TEMPLATE");
        component1.setChartNodeContent("title");
        //component1.setChartExportButton(true);
        component1.setChartExpandCollapse(true);
        component1.setChartDraggable(true);
        component1.addDragAndDropListener(
                e ->
                        System.out.println(
                                "------ OrgChart updated - Item dragged: "
                                        + e.getDraggedItem().getName()
                                        + "------\n"
                                        + e.getOrgChart().getOrgChartItem().toString()));

        component1.addOnNodeClickListener(
                e -> Notification.show("Item clicked: " + e.getClickedItem().getName()));

        return component1;
    }

    private OrgChart getExample1() {
        OrgChartItem item1 = new OrgChartItem(1, "John Williams", "Director");
        item1.setData("mail", "jwilliams@example.com");
        item1.setClassName("blue-node");
        OrgChartItem item2 = new OrgChartItem(2, "Anna Thompson", "Administration");
        item2.setData("mail", "athomp@example.com");
        item2.setClassName("blue-node");
        OrgChartItem item3 =
                new OrgChartItem(
                        3, "Timothy Albert Henry Jones ", "Sub-Director of Administration Department");
        item3.setData("mail", "timothy.albert.jones@example.com");
        item1.setChildren(Arrays.asList(item2, item3));
        OrgChartItem item4 = new OrgChartItem(4, "Louise Night", "Department 1");
        item4.setData("mail", "lnight@example.com");
        OrgChartItem item5 = new OrgChartItem(5, "John Porter", "Department 2");
        item5.setData("mail", "jporter@example.com");
        OrgChartItem item6 = new OrgChartItem(6, "Charles Thomas", "Department 3");
        item6.setData("mail", "ctomas@example.com");
        item2.setChildren(Arrays.asList(item4, item5, item6));
        OrgChartItem item7 = new OrgChartItem(7, "Michael Wood", "Section 3.1");
        OrgChartItem item8 = new OrgChartItem(8, "Martha Brown", "Section 3.2");
        OrgChartItem item9 = new OrgChartItem(9, "Mary Parker", "Section 3.3");
        OrgChartItem item10 = new OrgChartItem(10, "Mary Williamson", "Section 3.4");
        item6.setChildren(Arrays.asList(item7, item8, item9, item10));
        return new OrgChart(item1);
    }
}

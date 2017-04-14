Sgrowth=[0.4049	0.55785	0.6208	0.6944	0.75135	0.7215	0.72845	0.78135	0.826684211	0.8284];
generation=[1 2 3 4 5 6 7 8 9 10];
Mgrowth=[0.48695	0.58545	0.6799	0.69235	0.65805	0.6404	0.5266	0.65375	0.537	0.4937];
Mgrowth2=[0.55185	0.71705	0.6897	0.7062	0.57455	0.67045	0.6336	0.7277	0.76765	0.7502];
 
y =[0.4049	0.48695	0.55185; 0.55785 0.58545	0.71705; 0.6208	0.6799	0.6897; 0.6944	0.69235	0.7062; 0.75135	0.65805	0.57455; 0.7215	0.6404	0.67045; 0.72845	0.5266	0.6336; 0.78135	0.65375	0.7277; 0.826684211	0.537	0.76765; 0.8007	0.4937	0.7502];
b=bar(y);
b(1).BarWidth = 1;
b(2).BarWidth = 1;
b(3).BarWidth = 1;
b(2).LineStyle = '--';
b(2).LineWidth=1.5;
b(3).LineStyle = ':';
b(3).LineWidth=1.5
l = cell(1,3);
l{1}='Single Species Growth Rate '; l{2}='Multiple Species Growth Rate1'; l{3}='Multiple Species Growth Rate2';    
legend(b,l);
xlabel('Generations');
ylabel('Average Growth Rate')
title(' Relation Among Growth Rate of Single and Multiple Species');


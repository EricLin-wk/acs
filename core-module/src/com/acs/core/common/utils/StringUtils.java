/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.utils.StringUtils
   Module Description   :

   Date Created      : 2012/11/16
   Original Author   : tw4149
   Team              : 
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONSerializer;

import org.apache.commons.betwixt.io.BeanWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.common.exception.CoreException;

/**
 * @author tw4149
 * 
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
	private static Logger logger = LoggerFactory.getLogger(StringUtils.class);
	private static String jtPy = "万与丑专业丛东丝丢两严丧个丬丰临为丽举么义乌乐乔习乡书买乱争于亏云亘亚产亩亲亵亸亿仅从仑仓仪们价众优伙会伛伞伟传伤伥伦伧伪伫体余佣佥侠侣侥侦侧侨侩侪侬俣俦俨俩俪俭债倾偬偻偾偿傥傧储傩儿兑兖党兰关兴兹养兽冁内冈册写军农冢冯冲决况冻净凄凉凌减凑凛几凤凫凭凯击凼凿刍划刘则刚创删别刬刭刽刿剀剂剐剑剥剧劝办务劢动励劲劳势勋勐勚匀匦匮区医华协单卖卢卤卧卫却卺厂厅历厉压厌厍厕厢厣厦厨厩厮县参叆叇双发变叙叠叶号叹叽吁后吓吕吗吣吨听启吴呒呓呕呖呗员呙呛呜咏咔咙咛咝咤咴咸哌响哑哒哓哔哕哗哙哜哝哟唛唝唠唡唢唣唤唿啧啬啭啮啰啴啸喷喽喾嗫呵嗳嘘嘤嘱噜噼嚣嚯团园囱围囵国图圆圣圹场坂坏块坚坛坜坝坞坟坠垄垅垆垒垦垧垩垫垭垯垱垲垴埘埙埚埝埯堑堕塆墙壮声壳壶壸处备复够头夸夹夺奁奂奋奖奥妆妇妈妩妪妫姗姜娄娅娆娇娈娱娲娴婳婴婵婶媪嫒嫔嫱嬷孙学孪宁宝实宠审宪宫宽宾寝对寻导寿将尔尘尧尴尸尽层屃屉届属屡屦屿岁岂岖岗岘岙岚岛岭岳岽岿峃峄峡峣峤峥峦崂崃崄崭嵘嵚嵛嵝嵴巅巩巯币帅师帏帐帘帜带帧帮帱帻帼幂幞干并广庄庆庐庑库应庙庞废庼廪开异弃张弥弪弯弹强归当录彟彦彻径徕御忆忏忧忾怀态怂怃怄怅怆怜总怼怿恋恳恶恸恹恺恻恼恽悦悫悬悭悯惊惧惨惩惫惬惭惮惯愍愠愤愦愿慑慭憷懑懒懔戆戋戏戗战戬户扎扑扦执扩扪扫扬扰抚抛抟抠抡抢护报担拟拢拣拥拦拧拨择挂挚挛挜挝挞挟挠挡挢挣挤挥挦捞损捡换捣据捻掳掴掷掸掺掼揸揽揿搀搁搂搅携摄摅摆摇摈摊撄撑撵撷撸撺擞攒敌敛数斋斓斗斩断无旧时旷旸昙昼昽显晋晒晓晔晕晖暂暧札术朴机杀杂权条来杨杩杰极构枞枢枣枥枧枨枪枫枭柜柠柽栀栅标栈栉栊栋栌栎栏树栖样栾桊桠桡桢档桤桥桦桧桨桩梦梼梾检棂椁椟椠椤椭楼榄榇榈榉槚槛槟槠横樯樱橥橱橹橼檐檩欢欤欧歼殁殇残殒殓殚殡殴毁毂毕毙毡毵氇气氢氩氲汇汉污汤汹沓沟没沣沤沥沦沧沨沩沪沵泞泪泶泷泸泺泻泼泽泾洁洒洼浃浅浆浇浈浉浊测浍济浏浐浑浒浓浔浕涂涌涛涝涞涟涠涡涢涣涤润涧涨涩淀渊渌渍渎渐渑渔渖渗温游湾湿溃溅溆溇滗滚滞滟滠满滢滤滥滦滨滩滪漤潆潇潋潍潜潴澜濑濒灏灭灯灵灾灿炀炉炖炜炝点炼炽烁烂烃烛烟烦烧烨烩烫烬热焕焖焘煅煳熘爱爷牍牦牵牺犊犟状犷犸犹狈狍狝狞独狭狮狯狰狱狲猃猎猕猡猪猫猬献獭玑玙玚玛玮环现玱玺珉珏珐珑珰珲琎琏琐琼瑶瑷璇璎瓒瓮瓯电画畅畲畴疖疗疟疠疡疬疮疯疱疴痈痉痒痖痨痪痫痴瘅瘆瘗瘘瘪瘫瘾瘿癞癣癫癯皑皱皲盏盐监盖盗盘眍眦眬着睁睐睑瞒瞩矫矶矾矿砀码砖砗砚砜砺砻砾础硁硅硕硖硗硙硚确碱碍碛碜碱碹磙礼祎祢祯祷祸禀禄禅离秃秆种积称秽秾稆税稣稳穑穷窃窍窑窜窝窥窦窭竖竞笃笋笔笕笺笼笾筑筚筛筜筝筹签简箓箦箧箨箩箪箫篑篓篮篱簖籁籴类籼粜粝粤粪粮糁糇紧絷纟纠纡红纣纤纥约级纨纩纪纫纬纭纮纯纰纱纲纳纴纵纶纷纸纹纺纻纼纽纾线绀绁绂练组绅细织终绉绊绋绌绍绎经绐绑绒结绔绕绖绗绘给绚绛络绝绞统绠绡绢绣绤绥绦继绨绩绪绫绬续绮绯绰绱绲绳维绵绶绷绸绹绺绻综绽绾绿缀缁缂缃缄缅缆缇缈缉缊缋缌缍缎缏缐缑缒缓缔缕编缗缘缙缚缛缜缝缞缟缠缡缢缣缤缥缦缧缨缩缪缫缬缭缮缯缰缱缲缳缴缵罂网罗罚罢罴羁羟羡翘翙翚耢耧耸耻聂聋职聍联聩聪肃肠肤肷肾肿胀胁胆胜胧胨胪胫胶脉脍脏脐脑脓脔脚脱脶脸腊腌腘腭腻腼腽腾膑臜舆舣舰舱舻艰艳艹艺节芈芗芜芦苁苇苈苋苌苍苎苏苘苹茎茏茑茔茕茧荆荐荙荚荛荜荞荟荠荡荣荤荥荦荧荨荩荪荫荬荭荮药莅莜莱莲莳莴莶获莸莹莺莼萚萝萤营萦萧萨葱蒇蒉蒋蒌蓝蓟蓠蓣蓥蓦蔷蔹蔺蔼蕲蕴薮藁藓虏虑虚虫虬虮虽虾虿蚀蚁蚂蚕蚝蚬蛊蛎蛏蛮蛰蛱蛲蛳蛴蜕蜗蜡蝇蝈蝉蝎蝼蝾螀螨蟏衅衔补衬衮袄袅袆袜袭袯装裆裈裢裣裤裥褛褴襁襕见观觃规觅视觇览觉觊觋觌觍觎觏觐觑觞触觯詟誉誊讠计订讣认讥讦讧讨让讪讫训议讯记讱讲讳讴讵讶讷许讹论讻讼讽设访诀证诂诃评诅识诇诈诉诊诋诌词诎诏诐译诒诓诔试诖诗诘诙诚诛诜话诞诟诠诡询诣诤该详诧诨诩诪诫诬语诮误诰诱诲诳说诵诶请诸诹诺读诼诽课诿谀谁谂调谄谅谆谇谈谊谋谌谍谎谏谐谑谒谓谔谕谖谗谘谙谚谛谜谝谞谟谠谡谢谣谤谥谦谧谨谩谪谫谬谭谮谯谰谱谲谳谴谵谶谷豮贝贞负贠贡财责贤败账货质贩贪贫贬购贮贯贰贱贲贳贴贵贶贷贸费贺贻贼贽贾贿赀赁赂赃资赅赆赇赈赉赊赋赌赍赎赏赐赑赒赓赔赕赖赗赘赙赚赛赜赝赞赟赠赡赢赣赪赵赶趋趱趸跃跄跖跞践跶跷跸跹跻踊踌踪踬踯蹑蹒蹰蹿躏躜躯车轧轨轩轪轫转轭轮软轰轱轲轳轴轵轶轷轸轹轺轻轼载轾轿辀辁辂较辄辅辆辇辈辉辊辋辌辍辎辏辐辑辒输辔辕辖辗辘辙辚辞辩辫边辽达迁过迈运还这进远违连迟迩迳迹适选逊递逦逻遗遥邓邝邬邮邹邺邻郁郄郏郐郑郓郦郧郸酝酦酱酽酾酿释里钜鉴銮錾钆钇针钉钊钋钌钍钎钏钐钑钒钓钔钕钖钗钘钙钚钛钝钞钟钠钡钢钣钤钥钦钧钨钩钪钫钬钭钮钯钰钱钲钳钴钵钶钷钸钹钺钻钼钽钾钿铀铁铂铃铄铅铆铈铉铊铋铍铎铏铐铑铒铕铗铘铙铚铛铜铝铞铟铠铡铢铣铤铥铦铧铨铪铫铬铭铮铯铰铱铲铳铴铵银铷铸铹铺铻铼铽链铿销锁锂锃锄锅锆锇锈锉锊锋锌锍锎锏锐锑锒锓锔锕锖锗错锚锜锞锟锠锡锢锣锤锥锦锨锩锫锬锭键锯锰锱锲锳锴锵锶锷锸锹锺锻锼锽锾锿镀镁镂镃镆镇镈镉镊镌镍镎镏镐镑镒镕镖镗镙镚镛镜镝镞镟镠镡镢镣镤镥镦镧镨镩镪镫镬镭镮镯镰镱镲镳镴镶长门闩闪闫闬闭问闯闰闱闲闳间闵闶闷闸闹闺闻闼闽闾闿阀阁阂阃阄阅阆阇阈阉阊阋阌阍阎阏阐阑阒阓阔阕阖阗阘阙阚阛队阳阴阵阶际陆陇陈陉陕陧陨险随隐隶隽难雏雠雳雾霁霉霭靓静靥鞑鞒鞯鞴韦韧韨韩韪韫韬韵页顶顷顸项顺须顼顽顾顿颀颁颂颃预颅领颇颈颉颊颋颌颍颎颏颐频颒颓颔颕颖颗题颙颚颛颜额颞颟颠颡颢颣颤颥颦颧风飏飐飑飒飓飔飕飖飗飘飙飚飞飨餍饤饥饦饧饨饩饪饫饬饭饮饯饰饱饲饳饴饵饶饷饸饹饺饻饼饽饾饿馀馁馂馃馄馅馆馇馈馉馊馋馌馍馎馏馐馑馒馓馔馕马驭驮驯驰驱驲驳驴驵驶驷驸驹驺驻驼驽驾驿骀骁骂骃骄骅骆骇骈骉骊骋验骍骎骏骐骑骒骓骔骕骖骗骘骙骚骛骜骝骞骟骠骡骢骣骤骥骦骧髅髋髌鬓魇魉鱼鱽鱾鱿鲀鲁鲂鲄鲅鲆鲇鲈鲉鲊鲋鲌鲍鲎鲏鲐鲑鲒鲓鲔鲕鲖鲗鲘鲙鲚鲛鲜鲝鲞鲟鲠鲡鲢鲣鲤鲥鲦鲧鲨鲩鲪鲫鲬鲭鲮鲯鲰鲱鲲鲳鲴鲵鲶鲷鲸鲹鲺鲻鲼鲽鲾鲿鳀鳁鳂鳃鳄鳅鳆鳇鳈鳉鳊鳋鳌鳍鳎鳏鳐鳑鳒鳓鳔鳕鳖鳗鳘鳙鳛鳜鳝鳞鳟鳠鳡鳢鳣鸟鸠鸡鸢鸣鸤鸥鸦鸧鸨鸩鸪鸫鸬鸭鸮鸯鸰鸱鸲鸳鸴鸵鸶鸷鸸鸹鸺鸻鸼鸽鸾鸿鹀鹁鹂鹃鹄鹅鹆鹇鹈鹉鹊鹋鹌鹍鹎鹏鹐鹑鹒鹓鹔鹕鹖鹗鹘鹚鹛鹜鹝鹞鹟鹠鹡鹢鹣鹤鹥鹦鹧鹨鹩鹪鹫鹬鹭鹯鹰鹱鹲鹳鹴鹾麦麸黄黉黡黩黪黾鼋鼌鼍鼗鼹齄齐齑齿龀龁龂龃龄龅龆龇龈龉龊龋龌龙龚龛龟志制咨只里系范松没尝尝闹面准钟别闲干尽脏拼";
	private static String ftPy = "万与丑专业丛东丝丢两严丧个爿丰临为丽举麼义乌乐乔习乡书买乱争於亏云亘亚产亩亲亵嚲亿仅从仑仓仪们价众优夥会伛伞伟传伤伥伦伧伪伫体余佣佥侠侣侥侦侧侨侩侪侬俣俦俨俩俪俭债倾偬偻偾偿傥傧储傩儿兑兖党兰关兴兹养兽冁内冈册写军农冢冯冲决况冻净凄凉淩减凑凛几凤凫凭凯击氹凿刍划刘则刚创删别剗刭刽刿剀剂剐剑剥剧劝办务劢动励劲劳势勋猛勩匀匦匮区医华协单卖卢卤卧卫却卺厂厅历厉压厌厍厕厢厣厦厨厩厮县参靉靆双发变叙叠叶号叹叽吁后吓吕吗唚吨听启吴呒呓呕呖呗员呙呛呜咏哢咙咛噝吒噅咸呱响哑哒哓哔哕哗哙哜哝哟唛嗊唠啢唢唕唤呼啧啬啭啮罗嘽啸喷喽喾嗫嗬嗳嘘嘤嘱噜劈嚣谑团园囱围囵国图圆圣圹场阪坏块坚坛坜坝坞坟坠垄垄垆垒垦坰垩垫垭墶壋垲堖埘埙埚垫垵堑堕壪墙壮声壳壶壼处备复够头夸夹夺奁奂奋奖奥妆妇妈妩妪妫姗姜娄娅娆娇娈娱娲娴嫿婴婵婶媪嫒嫔嫱嬷孙学孪宁宝实宠审宪宫宽宾寝对寻导寿将尔尘尧尴尸尽层屭屉届属屡屦屿岁岂岖岗岘嶴岚岛岭岳岽岿嶨峄峡嶢峤峥峦崂崃嶮崭嵘嶔嵛嵝脊巅巩巯币帅师帏帐帘帜带帧帮帱帻帼幂襆干并广庄庆庐庑库应庙庞废廎廪开异弃张弥弪弯弹强归当录彠彦彻径徕御忆忏忧忾怀态怂怃怄怅怆怜总怼怿恋恳恶恸恹恺恻恼恽悦悫悬悭悯惊惧惨惩惫惬惭惮惯闵愠愤愦愿慑憖怵懑懒懔戆戋戏戗战戬户扎扑扡执扩扪扫扬扰抚抛抟抠抡抢护报担拟拢拣拥拦拧拨择挂挚挛掗挝挞挟挠挡挢挣挤挥撏捞损捡换捣据捻掳掴掷掸掺掼摣揽揿搀搁搂搅携摄摅摆摇摈摊撄撑撵撷撸撺擞攒敌敛数斋斓斗斩断无旧时旷暘昙昼曨显晋晒晓晔晕晖暂暧札术朴机杀杂权条来杨杩杰极构枞枢枣枥梘枨枪枫枭柜柠柽栀栅标栈栉栊栋栌栎栏树栖样栾棬桠桡桢档桤桥桦桧桨桩梦檮棶检棂椁椟椠椤椭楼榄榇榈榉檟槛槟槠横樯樱橥橱橹橼檐檩欢欤欧歼殁殇残殒殓殚殡殴毁毂毕毙毡毵氇气氢氩氲汇汉污汤汹遝沟没沣沤沥沦沧渢沩沪濔泞泪泶泷泸泺泻泼泽泾洁洒洼浃浅浆浇浈溮浊测浍济浏滻浑浒浓浔濜涂涌涛涝涞涟涠涡溳涣涤润涧涨涩淀渊渌渍渎渐渑渔沈渗温游湾湿溃溅溆漊滗滚滞滟滠满滢滤滥滦滨滩澦滥潆潇潋潍潜潴澜濑濒灏灭灯灵灾灿炀炉炖炜炝点炼炽烁烂烃烛烟烦烧烨烩烫烬热焕焖焘煆糊溜爱爷牍犛牵牺犊强状犷獁犹狈麅獮狞独狭狮狯狰狱狲猃猎猕猡猪猫猬献獭玑璵瑒玛玮环现瑲玺瑉珏珐珑璫珲璡琏琐琼瑶瑷璿璎瓒瓮瓯电画畅佘畴疖疗疟疠疡鬁疮疯疱屙痈痉痒瘂痨痪痫痴瘅瘮瘗瘘瘪瘫瘾瘿癞癣癫臒皑皱皲盏盐监盖盗盘瞘眥胧著睁睐睑瞒瞩矫矶矾矿砀码砖砗砚碸砺砻砾础硜矽硕硖硗磑礄确碱碍碛碜碱镟滚礼禕祢祯祷祸禀禄禅离秃秆种积称秽穠穭税稣稳穑穷窃窍窑窜窝窥窦窭竖竞笃笋笔笕笺笼笾筑筚筛簹筝筹签简籙箦箧箨箩箪箫篑篓篮篱簖籁籴类秈粜粝粤粪粮糁糇紧絷糸纠纡红纣纤纥约级纨纩纪纫纬纭紘纯纰纱纲纳紝纵纶纷纸纹纺紵紖纽纾线绀絏绂练组绅细织终绉绊绋绌绍绎经绐绑绒结絝绕絰绗绘给绚绛络绝绞统绠绡绢绣綌绥绦继绨绩绪绫緓续绮绯绰緔绲绳维绵绶绷绸綯绺绻综绽绾绿缀缁缂缃缄缅缆缇缈缉縕缋缌缍缎缏线缑缒缓缔缕编缗缘缙缚缛缜缝縗缟缠缡缢缣缤缥缦缧缨缩缪缫缬缭缮缯韁缱缲缳缴缵罂网罗罚罢罴羁羟羡翘翽翬耮耧耸耻聂聋职聍联聩聪肃肠肤膁肾肿胀胁胆胜胧腖胪胫胶脉脍脏脐脑脓脔脚脱脶脸腊腌膕腭腻腼腽腾膑臢舆舣舰舱舻艰艳艸艺节芈芗芜芦苁苇苈苋苌苍苎苏檾苹茎茏茑茔茕茧荆荐薘荚荛荜荞荟荠荡荣荤荥荦荧荨荩荪荫蕒荭葤药莅蓧莱莲莳莴莶获莸莹莺莼蘀萝萤营萦萧萨葱蒇蒉蒋蒌蓝蓟蓠蓣蓥蓦蔷蔹蔺蔼蕲蕴薮槁藓虏虑虚虫虬虮虽虾虿蚀蚁蚂蚕蚝蚬蛊蛎蛏蛮蛰蛱蛲蛳蛴蜕蜗蜡蝇蝈蝉蝎蝼蝾螿蟎蠨衅衔补衬衮袄袅褘袜袭襏装裆褌裢裣裤裥褛褴襁襴见观覎规觅视觇览觉觊觋觌覥觎觏觐觑觞触觯讋誉誊訁计订讣认讥讦讧讨让讪讫训议讯记訒讲讳讴讵讶讷许讹论訩讼讽设访诀证诂诃评诅识詗诈诉诊诋诌词诎诏詖译诒诓诔试诖诗诘诙诚诛诜话诞诟诠诡询诣诤该详诧诨诩譸诫诬语诮误诰诱诲诳说诵诶请诸诹诺读诼诽课诿谀谁谂调谄谅谆谇谈谊谋谌谍谎谏谐谑谒谓谔谕谖谗谘谙谚谛谜谝諝谟谠谡谢谣谤諡谦谧谨谩谪谫谬谭谮谯谰谱谲谳谴谵谶谷豶贝贞负貟贡财责贤败账货质贩贪贫贬购贮贯贰贱贲贳贴贵贶贷贸费贺贻贼贽贾贿赀赁赂赃资赅赆赇赈赉赊赋赌齎赎赏赐贔周赓赔赕赖賵赘赙赚赛赜赝赞贇赠赡赢赣赬赵赶趋趱趸跃跄跖跞践躂跷跸跹跻踊踌踪踬踯蹑蹒蹰蹿躏躜躯车轧轨轩軑轫转轭轮软轰軲轲轳轴轵轶軤轸轹轺轻轼载轾轿輈辁辂较辄辅辆辇辈辉辊辋輬辍辎辏辐辑轀输辔辕辖辗辘辙辚辞辩辫边辽达迁过迈运还这进远违连迟迩迳迹适选逊递逦逻遗遥邓邝邬邮邹邺邻郁郤郏郐郑郓郦郧郸酝发酱酽酾酿释裏钜鉴銮錾钆钇针钉钊钋钌钍釺钏钐鈒钒钓钔钕鍚钗鈃钙鈈钛钝钞钟钠钡钢钣钤钥钦钧钨钩钪钫钬钭钮钯钰钱钲钳钴钵钶鉕钸钹钺钻钼钽钾钿铀铁铂铃铄铅铆铈铉铊铋铍铎鉶铐铑铒铕铗鋣铙銍铛铜铝銱铟铠铡铢铣铤铥銛铧铨铪铫铬铭铮铯铰铱铲铳铴铵银铷铸铹铺鋙铼铽链铿销锁锂鋥锄锅锆锇锈锉锊锋锌鋶鐦鐧锐锑锒锓锔锕锖锗错锚錡锞锟錩锡锢锣锤锥锦鍁锩錇锬锭键锯锰锱锲鍈锴锵锶锷锸锹钟锻锼鍠锾鎄镀镁镂鎡镆镇鎛镉镊镌镍鎿镏镐镑镒鎔镖镗镙鏰镛镜镝镞镟鏐镡钁镣镤鑥镦镧镨鑹镪镫镬镭环镯镰镱鑔镳鑞镶长门闩闪闫閈闭问闯闰闱闲闳间闵闶闷闸闹闺闻闼闽闾闓阀阁阂阃阄阅阆闍阈阉阊阋阌阍阎阏阐阑阒闠阔阕阖阗闒阙阚闤队阳阴阵阶际陆陇陈陉陕陧陨险随隐隶隽难雏雠雳雾霁霉霭靓静靥鞑鞽鞯韝韦韧韍韩韪韫韬韵页顶顷顸项顺须顼顽顾顿颀颁颂颃预颅领颇颈颉颊頲颌颍熲颏颐频頮颓颔頴颖颗题顒颚颛颜额颞颟颠颡颢纇颤顬颦颧风颺颭飑飒飓颸飕颻飀飘飙飙飞飨餍飣饥飥饧饨饩饪饫饬饭饮饯饰饱饲飿饴饵饶饷餄餎饺餏饼饽餖饿余馁餕餜馄馅馆餷馈餶馊馋饁馍餺馏馐馑馒饊馔饢马驭驮驯驰驱馹驳驴驵驶驷驸驹驺驻驼驽驾驿骀骁骂駰骄骅骆骇骈驫骊骋验騂駸骏骐骑骒骓騌驌骖骗骘騤骚骛骜骝骞骟骠骡骢骣骤骥驦骧髅髋髌鬓魇魉鱼魛魢鱿魨鲁鲂魺鮁鮃鲶鲈鮋鮓鲋鮊鲍鲎鮍鲐鲑鲒鮳鲔鲕鮦鰂鮜鱠鲚鲛鲜鮺鲞鲟鲠鲡鲢鲣鲤鲥鲦鲧鲨鲩鮶鲫鯒鲭鲮鯕鲰鲱鲲鲳鯝鲵鲶鲷鲸鯵鯴鲻鱝鲽鰏鱨鯷鰮鰃鳃鳄鳅鳆鳇鰁鱂鯿鰠鳌鳍鳎鳏鳐鰟鰜鳓鳔鳕鳖鳗鰵鱅鰼鳜鳝鳞鳟鱯鱤鳢鱣鸟鸠鸡鸢鸣鳲鸥鸦鶬鸨鸩鸪鸫鸬鸭鴞鸯鴒鸱鸲鸳鴬鸵鸶鸷鸸鸹鸺鴴鵃鸽鸾鸿鵐鹁鹂鹃鹄鹅鹆鹇鹈鹉鹊鶓鹌鶤鹎鹏鵮鹑鶊鵷鷫鹕鶡鹗鹘鹚鶥鹜鷊鹞鶲鶹鶺鷁鹣鹤鷖鹦鹧鹨鹩鹪鹫鹬鹭鸇鹰鸌鸏鹳鸘鹾麦麸黄黉黶黩黪黾鼋鼂鼍鞀鼹齇齐齑齿龀齕齗龃龄龅龆龇龈龉龊龋龌龙龚龛龟志制谘只里系范松冇尝尝哄面准钟别闲乾尽脏拚";

	/**
	 * 
	 */
	public StringUtils() {
		// TODO Auto-generated constructor stub
	}

	public static final Pattern titlePattern = Pattern.compile("<tittle<(.*)<\\/tittle>", Pattern.CASE_INSENSITIVE);
	public static final Pattern numberPattern = Pattern.compile("^([\\d]+)", Pattern.CASE_INSENSITIVE);

	public static String parseTitle(String html) {
		String result = "";
		Matcher matcher = titlePattern.matcher(html);
		boolean matchFound = matcher.find();
		if (matchFound) {
			result = matcher.group(1);
		}
		return result;
	}

	public static String parseNumber(String string) {
		String result = "";
		Matcher matcher = numberPattern.matcher(string);
		boolean matchFound = matcher.find();
		if (matchFound) {
			result = matcher.group(1);
		}
		return result;
	}

	public static String formatNumber(int number, int size, char digit, boolean flag) {
		return formatNumber(String.valueOf(number), size, String.valueOf(digit), flag);
	}

	public static String formatNumber(int number, int size, String digit, boolean flag) {
		return formatNumber(String.valueOf(number), size, digit, flag);
	}

	public static String formatNumber(String number, int size, char digit, boolean flag) {
		return formatNumber(String.valueOf(number), size, String.valueOf(digit), flag);
	}

	public static String formatNumber(String number, int size, String digit, boolean flag) {
		StringBuffer result = new StringBuffer();
		result.append(number);
		while (result.length() < size) {
			if (flag) {
				result.insert(0, digit);
			} else {
				result.append(digit);
			}
		}
		return result.toString();
	}

	public static String truncateNicely(String str, int lower, int upper, String appendToEnd) { // NOPMD by jeffma on
		// strip markup from the string
		if (StringUtils.isBlank(str)) {
			return "";
		}
		String str2 = removeHTML(str, false);
		boolean diff = (str2.length() < str.length());

		// quickly adjust the upper if it is set lower than 'lower'
		if (upper < lower) {
			upper = lower;
		}

		// now determine if the string fits within the upper limit
		// if it does, go straight to return, do not pass 'go' and collect $200
		if (str2.length() > upper) {
			// the magic location int
			int loc;

			// first we determine where the next space appears after lower
			loc = str2.lastIndexOf(' ', upper);

			// now we'll see if the location is greater than the lower limit
			if (loc >= lower) {
				// yes it was, so we'll cut it off here
				str2 = str2.substring(0, loc);
			} else {
				// no it wasnt, so we'll cut it off at the upper limit
				str2 = str2.substring(0, upper);
				loc = upper;
			}

			// HTML was removed from original str
			if (diff) {

				// location of last space in truncated string
				loc = str2.lastIndexOf(' ', loc);

				// get last "word" in truncated string (add 1 to loc to
				// eliminate space
				String str3 = str2.substring(loc + 1);

				// find this fragment in original str, from 'loc' position
				loc = str.indexOf(str3, loc) + str3.length();

				// get truncated string from original str, given new 'loc'
				str2 = str.substring(0, loc);

				// get all the HTML from original str after loc
				str3 = extractHTML(str.substring(loc));

				// remove any tags which generate visible HTML
				// This call is unecessary, all HTML has already been stripped
				// str3 = removeVisibleHTMLTags(str3);

				// append the appendToEnd String and
				// add extracted HTML back onto truncated string
				str = str2 + appendToEnd + str3;
			} else {
				// the string was truncated, so we append the appendToEnd String
				str = str2 + appendToEnd;
			}
		}
		return str;
	}

	/**
	 * Remove occurences of html, defined as any text between the characters "&lt;" and "&gt;". Optionally replace HTML
	 * tags with a space.
	 * 
	 * @param str
	 * @param addSpace
	 * @return string
	 */
	public static String removeHTML(String str, boolean addSpace) {
		if (str == null) {
			return "";
		}
		StringBuffer ret = new StringBuffer(str.length());
		int start = 0;
		int beginTag = str.indexOf("<");
		int endTag = 0;
		if (beginTag == -1) {
			return str;
		}

		while (beginTag >= start) {
			if (beginTag > 0) {
				ret.append(str.substring(start, beginTag));

				// replace each tag with a space (looks better)
				if (addSpace) {
					ret.append(" ");
				}
			}
			endTag = str.indexOf(">", beginTag);

			// if endTag found move "cursor" forward
			if (endTag > -1) {
				start = endTag + 1;
				beginTag = str.indexOf("<", start);
			}
			// if no endTag found, get rest of str and break
			else {
				ret.append(str.substring(beginTag));
				break;
			}
		}
		// append everything after the last endTag
		if ((endTag > -1) && (endTag + 1 < str.length())) {
			ret.append(str.substring(endTag + 1));
		}
		return ret.toString().trim();
	}

	/**
	 * Extract (keep) JUST the HTML from the String.
	 * 
	 * @param str
	 * @return string
	 */
	public static String extractHTML(String str) {
		if (str == null) {
			return "";
		}
		StringBuffer ret = new StringBuffer(str.length());
		int start = 0;
		int beginTag = str.indexOf("<");
		int endTag = 0;
		if (beginTag == -1) {
			return str;
		}

		while (beginTag >= start) {
			endTag = str.indexOf(">", beginTag);

			// if endTag found, keep tag
			if (endTag > -1) {
				ret.append(str.substring(beginTag, endTag + 1));

				// move start forward and find another tag
				start = endTag + 1;
				beginTag = str.indexOf("<", start);
			}
			// if no endTag found, break
			else {
				break;
			}
		}
		return ret.toString();
	}

	/**
	 * @param src
	 *           ex. a,c,b,d
	 * @param sort
	 *           ex. 1,3,2,4
	 * @return ex. a,b,c,d
	 */
	public static String sortString(String src, String sort) {
		try {
			if (isBlank(src) || isBlank(sort)) {
				logger.warn("src or sort is null, src:{}, sort:{}", src, sort);
				return src;
			}
			List<String> srcList = new ArrayList();
			String[] tmpStrArray = StringUtils.split(src, ",");
			for (String s : StringUtils.split(src, ",")) {
				srcList.add(s);
			}
			String[] sortList = StringUtils.split(sort, ",");
			if (tmpStrArray.length != sortList.length) {
				logger.warn("length incorrect, src:{}, sort:{}", srcList.size(), sortList.length);
				return src;
			}
			for (int i = 0; i < sortList.length; i++) {
				// logger.debug("set {} is {}", Integer.valueOf(sortList[i]) - 1, tmpStrArray[i]);
				srcList.set(Integer.valueOf(sortList[i]) - 1, tmpStrArray[i]);
			}
			return StringUtils.join(srcList.toArray(), ",");
		} catch (Exception e) {
			logger.error("errors.utils.stringutils.sortstring", e);
		}
		return src;
	}

	public static String printExecption(String message, Exception e) {
		StringBuffer sb = new StringBuffer("");
		if (message != null) {
			sb.append(message + "\r\n");
		}
		sb.append(e.getMessage() + "\r\n");
		StackTraceElement[] stes = e.getStackTrace();
		for (StackTraceElement ste : stes) {
			sb.append(ste.toString() + "\r\n");
		}
		return sb.toString();

	}

	public static String mapToString(Map<String, String> map) {
		StringBuilder stringBuilder = new StringBuilder();

		for (String key : map.keySet()) {
			if (stringBuilder.length() > 0) {
				stringBuilder.append("&");
			}
			String value = map.get(key);
			try {
				stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));
				stringBuilder.append("=");
				stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("This method requires UTF-8 encoding support", e);
			}
		}
		return stringBuilder.toString();
	}

	public static Map<String, String> stringToMap(String input) {
		Map<String, String> map = new HashMap<String, String>();

		String[] nameValuePairs = input.split("&");
		for (String nameValuePair : nameValuePairs) {
			String[] nameValue = nameValuePair.split("=");
			try {
				map.put(URLDecoder.decode(nameValue[0], "UTF-8"),
						nameValue.length > 1 ? URLDecoder.decode(nameValue[1], "UTF-8") : "");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("This method requires UTF-8 encoding support", e);
			}
		}
		return map;
	}

	public static String simplized(String st) {
		StringBuffer stReturn = new StringBuffer();
		for (int i = 0; i < st.length(); i++) {
			char temp = st.charAt(i);
			if (jtPy.indexOf(temp) != -1) {
				stReturn.append(ftPy.charAt(jtPy.indexOf(temp)));
			} else {
				stReturn.append(temp);
			}
		}
		return stReturn.toString();
	}

	public static String traditionalized(String st) {
		StringBuffer stReturn = new StringBuffer();
		for (int i = 0; i < st.length(); i++) {
			char temp = st.charAt(i);
			if (ftPy.indexOf(temp) != -1) {
				stReturn.append(jtPy.charAt(ftPy.indexOf(temp)));
			} else {
				stReturn.append(temp);
			}
		}
		return stReturn.toString();
	}

	public static String toXML(Object entity, boolean prettyPrint) throws CoreException {
		String xml = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BeanWriter beanWriter = new BeanWriter(baos);
			if (prettyPrint) {
				beanWriter.enablePrettyPrint();
			}
			beanWriter.write(entity);
			xml = baos.toString();
			beanWriter.flush();
			baos.flush();
			beanWriter.close();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CoreException("to xml fail", e);
		}
		return xml;
	}

	public static String toJSON(Object entity) throws CoreException {
		String json = null;
		try {
			json = JSONSerializer.toJSON(entity).toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CoreException("to xml fail", e);
		}
		return json;
	}
}
